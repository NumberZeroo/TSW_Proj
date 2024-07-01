package model.invoice;

import model.AbstractDAO;
import model.infoConsegna.InfoConsegnaBean;
import model.orderItem.OrderItemBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO extends AbstractDAO {
    public InvoiceBean doRetrieveByOrderId(long orderId) throws SQLException {
        InvoiceBean invoiceBean = new InvoiceBean();

        // Ottenimento informazioni di spedizione
        String query = "SELECT dataOrdine, citta, cap, via, destinatario FROM Ordine, InfoConsegna WHERE Ordine.infoConsegna = InfoConsegna.id AND Ordine.id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                invoiceBean.setOrderId(orderId);
                invoiceBean.setCustomer(resultSet.getString("destinatario"));
                invoiceBean.setDate(resultSet.getDate("dataOrdine"));

                InfoConsegnaBean infoConsegnaBean = new InfoConsegnaBean();
                infoConsegnaBean.setVia(resultSet.getString("via"));
                infoConsegnaBean.setCap(resultSet.getInt("cap"));
                infoConsegnaBean.setCitta(resultSet.getString("citta"));

                invoiceBean.setInfoConsegna(infoConsegnaBean);
            } else {
                throw new SQLException("Nessun ordine con id: " + orderId + " trovato");
            }
        }

        // Ottenimento prodotti
        List<OrderItemBean> orderItemBeans = new ArrayList<>();
        query = "SELECT idProdotto, OrderItem.Prezzo, Quantita, OrderItem.Iva, Prodotto.Nome FROM OrderItem JOIN Prodotto ON OrderItem.idProdotto = Prodotto.id WHERE idOrdine = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setLong(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                OrderItemBean orderItemBean = new OrderItemBean();
                orderItemBean.setIdProdotto(resultSet.getLong("idProdotto"));
                orderItemBean.setPrezzo(resultSet.getDouble("Prezzo"));
                orderItemBean.setQuantita(resultSet.getInt("Quantita"));
                orderItemBean.setIva(Integer.parseInt(resultSet.getString("Iva")));
                orderItemBean.setNome(resultSet.getString("Nome"));
                orderItemBeans.add(orderItemBean);
            }
        }

        invoiceBean.setOrderItems(orderItemBeans);

        return invoiceBean;
    }
}
