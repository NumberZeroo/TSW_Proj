package model.orderItem;

import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import model.DAOInterface;
import java.sql.*;
import java.util.*;

public class OrderItemDAO extends AbstractDAO implements DAOInterface<OrderItemBean, Long> {
    public OrderItemDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public OrderItemBean doRetrieveByKey(long id) throws SQLException {
        String query = "SELECT * FROM OrderItem WHERE idItem = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractOrderItemFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Collection<OrderItemBean> doRetrieveAll(String order) throws SQLException {
        List<OrderItemBean> orderItems = new ArrayList<>();
        String query = "SELECT * FROM OrderItem";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                OrderItemBean orderItem = extractOrderItemFromResultSet(resultSet);
                orderItems.add(orderItem);
            }
        }
        return orderItems;
    }

    @Override
    public void doSave(OrderItemBean orderItem) throws SQLException {
        String query = "INSERT INTO OrderItem (IdOrdine, Prezzo, Quantita) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderItem.getIdOrdine());
            statement.setDouble(2, orderItem.getPrezzo());
            statement.setLong(3, orderItem.getQuantita());
            statement.executeUpdate();
        }
    }

    @Override
    public void doUpdate(OrderItemBean orderItem) throws SQLException {
        String query = "UPDATE OrderItem SET idProdotto = ?, IdOrdine = ?, Prezzo = ?, Quantita = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderItem.getIdProdotto());
            statement.setLong(2, orderItem.getIdOrdine());
            statement.setDouble(3, orderItem.getPrezzo());
            statement.setLong(4, orderItem.getQuantita());
            statement.setLong(5, orderItem.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM OrderItem WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    private OrderItemBean extractOrderItemFromResultSet(ResultSet resultSet) throws SQLException {
        OrderItemBean orderItem = new OrderItemBean();
        orderItem.setId(resultSet.getLong("id"));
        orderItem.setIdProdotto(resultSet.getLong("idProdotto"));
        orderItem.setIdOrdine(resultSet.getLong("IdOrdine"));
        orderItem.setPrezzo(resultSet.getDouble("Prezzo"));
        orderItem.setQuantita(resultSet.getInt("Quantita"));
        return orderItem;
    }
}
