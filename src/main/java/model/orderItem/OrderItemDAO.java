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
    public long doSave(OrderItemBean orderItem) throws SQLException {
        String query = "INSERT INTO OrderItem (IdOrdine, IdProdotto, Prezzo, Quantita, Iva, Nome) VALUES (?, ?, ?, ?, ?, ?)";
        long generatedKey = -1;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, orderItem.getIdOrdine());
            statement.setLong(2, orderItem.getIdProdotto());
            statement.setDouble(3, orderItem.getPrezzo());
            statement.setLong(4, orderItem.getQuantita());
            statement.setString(5, Integer.toString(orderItem.getIva()));
            statement.setString(6, orderItem.getNome());
            if (statement.executeUpdate() > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()){
                    generatedKey = resultSet.getLong(1);
                }
            }
        }
        return generatedKey;
    }

    /**
     * Manda "orderItems" come transazione
     * @param orderItems
     * @throws SQLException
     */
    public void doSaveAll(List<OrderItemBean> orderItems) throws SQLException {
        connection.setAutoCommit(false);

        try{
            for (OrderItemBean orderItem : orderItems) {
                doSave(orderItem);
            }
            connection.commit();
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public void doUpdate(OrderItemBean orderItem) throws SQLException {
        String query = "UPDATE OrderItem SET idProdotto = ?, IdOrdine = ?, Prezzo = ?, Quantita = ?, iva = ?, Nome = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderItem.getIdProdotto());
            statement.setLong(2, orderItem.getIdOrdine());
            statement.setDouble(3, orderItem.getPrezzo());
            statement.setLong(4, orderItem.getQuantita());
            statement.setString(5, Integer.toString(orderItem.getIva()));
            statement.setString(6, orderItem.getNome());
            statement.setLong(7, orderItem.getId());
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
        orderItem.setIva(Integer.parseInt(resultSet.getString("Iva")));
        orderItem.setNome(resultSet.getString("Nome"));
        return orderItem;
    }
}
