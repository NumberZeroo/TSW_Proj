package control;

import com.tswproject.tswproj.OutOfStockException;
import com.tswproject.tswproj.SessionFacade;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.orderItem.OrderItemBean;
import model.orderItem.OrderItemDAO;
import model.ordine.OrdineBean;
import model.ordine.OrdineDAO;
import model.prodotto.ProdottoBean;
import model.prodotto.ProdottoDAO;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.*;

@WebServlet("/api/orders/*")
public class CaptureOrderServlet extends HttpServlet {
    private static final String BASE_URL = "https://api-m.sandbox.paypal.com";
    private static final String CLIENT_ID = "CLIENT-ID";
    private static final String CLIENT_SECRET = "CLIENT-SECRET";

    private String generateAccessToken() throws IOException, URISyntaxException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            URI uri = new URIBuilder(BASE_URL + "/v1/oauth2/token").build();
            HttpPost post = new HttpPost(uri);
            post.setHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString((CLIENT_ID + ":" + CLIENT_SECRET).getBytes()));
            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

            List<NameValuePair> params = new ArrayList<>();
            params.add(new BasicNameValuePair("grant_type", "client_credentials"));
            post.setEntity(new UrlEncodedFormEntity(params));

            try (CloseableHttpResponse response = client.execute(post)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                JSONObject json = new JSONObject(responseBody);
                return json.getString("access_token");
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private JSONObject capturePayment(String orderId) throws IOException, URISyntaxException {
        String accessToken = generateAccessToken();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            URI uri = new URIBuilder(BASE_URL + "/v2/checkout/orders/" + orderId + "/capture").build();
            HttpPost post = new HttpPost(uri);
            post.setHeader("Authorization", "Bearer " + accessToken);
            post.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = client.execute(post)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                return new JSONObject(responseBody);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length == 3 && "capture".equals(pathParts[2])) {
                String orderId = pathParts[1];
                JSONObject captureData = null;
                try {
                    captureData = capturePayment(orderId);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
                resp.setContentType("application/json");
                resp.getWriter().write(captureData.toString());

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = req.getReader().readLine()) != null) {
                    sb.append(line);
                }

                JSONObject json = new JSONObject(sb.toString());
                long selectedOption = Long.parseLong(json.getString("selectedOption"));

                // TODO: qui aggiungi logica per creazione ordine
                String status = saveOrderToDB(new SessionFacade(req.getSession()), selectedOption) ? "success" : "error";
//                resp.setStatus(HttpServletResponse.SC_OK);
//                resp.setContentType("application/json");
//                resp.getWriter().write("{\"status\":\"" + status + "\"}");
//                resp.getWriter().flush();
                //resp.sendRedirect("/home.jsp");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL pattern");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing order ID");
        }
    }
    /**
     * Crea la struttura db per la gestione degli ordini (ordine e order items) e svuota il carrello
     * @param userSession sessione utente
     * @param idInfoConsegna selezione di metodo di spedizione
     * @return success
     */
    private boolean saveOrderToDB(SessionFacade userSession, long idInfoConsegna) {
        Map<Long, Integer> cartProducts = userSession.getCartProducts();
        if (cartProducts == null || cartProducts.isEmpty()) {
            return false;
        }
        if (idInfoConsegna < 0) {
            return false;
        }

        // 2. Crea un ordine
        OrdineBean ordine = new OrdineBean();
        ordine.setIdInfoConsegna(idInfoConsegna);
        ordine.setIdUtente(userSession.getUserId());

        long idOrdine;
        try(OrdineDAO ordineDAO = new OrdineDAO()) {
            idOrdine = ordineDAO.doSave(ordine);
        } catch (SQLException e) {
            System.out.println("Errore durante la creazione dell'ordine");
            e.printStackTrace();
            return false;
        }

        // 3. Crea gli orderItem
        Map<ProdottoBean, Integer> toBuy;
        try(ProdottoDAO prodottoDAO = new ProdottoDAO()) {
            toBuy = prodottoDAO.doRetrieveByKeys(cartProducts);
        } catch (SQLException e) {
            try(OrdineDAO ordineDAO = new OrdineDAO()) {
                ordineDAO.doDelete(idOrdine);
            }catch (SQLException s){
                System.out.println("Errore durante la procedura di free");
                s.printStackTrace();
                return false;
            }
            System.out.println("Errore durante la determinazione degli articoli");
            e.printStackTrace();
            return false;
        }

        List<OrderItemBean> itemsToBuy = new LinkedList<>();
        for (ProdottoBean prodottoBean : toBuy.keySet()) {
            OrderItemBean orderItemBean = new OrderItemBean();
            orderItemBean.setPrezzo(prodottoBean.getPrezzo());
            orderItemBean.setQuantita(toBuy.get(prodottoBean));
            orderItemBean.setIdOrdine(idOrdine);
            orderItemBean.setIdProdotto(prodottoBean.getId());
            orderItemBean.setIva(Integer.parseInt(prodottoBean.getIva()));
            orderItemBean.setNome(prodottoBean.getNome());
            itemsToBuy.add(orderItemBean);
        }


        try(OrderItemDAO orderItemDAO = new OrderItemDAO()){
            orderItemDAO.doSaveAll(itemsToBuy);
        } catch (SQLException e) {
            try(OrdineDAO ordineDAO = new OrdineDAO()) {
                ordineDAO.doDelete(idOrdine);
            } catch (SQLException ex) {
                System.out.println("Errore durante la procedura di free");
                ex.printStackTrace();
                return false;
            }
            System.out.println("Errore nel salvataggio dei prodotti dell'ordine");
            e.printStackTrace();
            return false;
        }

        // 4. Decrementa disponibilità

        try(ProdottoDAO prodottoDAO = new ProdottoDAO()){
            prodottoDAO.doUpdateQuantities(toBuy);
        } catch (OutOfStockException | SQLException e) {
            try(OrdineDAO ordineDAO = new OrdineDAO()) {
                ordineDAO.doDelete(idOrdine); // OrderItems eliminati per CASCADE
            } catch (SQLException ex) {
                System.out.println("Errore durante la procedura di free");
                ex.printStackTrace();
                return false;
            }
            System.out.println("Errore nel salvataggio dei prodotti dell'ordine");
            e.printStackTrace();
            return false;
        }

        // 5. Svuota carrello
        userSession.removeAllCartProducts();

        return true;
    }
}


