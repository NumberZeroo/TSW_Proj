package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.apache.hc.core5.net.URIBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@WebServlet("/api/orders")
public class CreateOrderServlet extends HttpServlet {
    private static final String BASE_URL = "https://api-m.sandbox.paypal.com";
    private static final String CLIENT_ID = "CLIENT-ID";
    private static final String CLIENT_SECRET = "CLIENT-SECRET";

    private String generateAccessToken() throws IOException {

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            URI uri = URI.create("https://api-m.sandbox.paypal.com/v1/oauth2/token");
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

    private JSONObject createOrder(String paymentSource, String price) throws IOException {
        String accessToken = generateAccessToken();
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            URI uri = new URIBuilder(BASE_URL + "/v2/checkout/orders").build();
            HttpPost post = new HttpPost(uri);
            post.setHeader("Authorization", "Bearer " + accessToken);
            post.setHeader("Content-Type", "application/json");

            JSONObject body = new JSONObject();
            body.put("intent", "CAPTURE");

            JSONObject amount = new JSONObject();
            amount.put("currency_code", "EUR");
            amount.put("value", price); // Puoi modificare questo valore in base alle tue esigenze

            JSONObject purchaseUnit = new JSONObject();
            purchaseUnit.put("amount", amount);

            body.put("purchase_units", new JSONArray().put(purchaseUnit));
            body.put("payment_source", new JSONObject().put(paymentSource, new JSONObject()));

            post.setEntity(new StringEntity(body.toString()));

            try (CloseableHttpResponse response = client.execute(post)) {
                String responseBody = EntityUtils.toString(response.getEntity());
                return new JSONObject(responseBody);
            }
        } catch (URISyntaxException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = req.getReader().readLine()) != null) {
            sb.append(line);
        }

        JSONObject json = new JSONObject(sb.toString());
        String paymentSource = json.getString("paymentSource");
        String price = json.getString("totalPrice");

        JSONObject order = createOrder(paymentSource, price);
        resp.setContentType("application/json");
        resp.getWriter().write(order.toString());
    }
}
