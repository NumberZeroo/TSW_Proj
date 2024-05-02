package model.orderItem;

import model.DAOInterface;
import model.ConnectionPool;
import java.sql.*;
import java.util.*;

public class OrderItemDAO implements DAOInterface<OrderItemBean, Long> {
    private Connection connection;

    public OrderItemDAO() {
        this.connection = ConnectionPool.getConnection();
    }

    @Override
    public OrderItemBean doRetrieveByKey(int id) throws SQLException {
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
            statement.setLong(2, orderItem.getPrezzo());
            statement.setLong(3, orderItem.getQuantita());
            statement.executeUpdate();
        }
    }

    @Override
    public void doUpdate(OrderItemBean orderItem) throws SQLException {
        String query = "UPDATE OrderItem SET IdOrdine = ?, Prezzo = ?, Quantita = ? WHERE idItem = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderItem.getIdOrdine());
            statement.setLong(2, orderItem.getPrezzo());
            statement.setLong(3, orderItem.getQuantita());
            statement.setLong(4, orderItem.getIdItem());
            statement.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM OrderItem WHERE idItem = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    private OrderItemBean extractOrderItemFromResultSet(ResultSet resultSet) throws SQLException {
        OrderItemBean orderItem = new OrderItemBean();
        orderItem.setIdItem(resultSet.getLong("idItem"));
        orderItem.setIdOrdine(resultSet.getLong("IdOrdine"));
        orderItem.setPrezzo(resultSet.getLong("Prezzo"));
        orderItem.setQuantita(resultSet.getLong("Quantita"));
        return orderItem;
    }

    public void close() {
        if (connection != null) {
            ConnectionPool.releaseConnection(connection);
            connection = null;
        }
    }
}
