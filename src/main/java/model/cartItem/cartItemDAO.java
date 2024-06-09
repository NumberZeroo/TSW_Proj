package model.cartItem;

import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import model.DAOInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class cartItemDAO extends AbstractDAO implements DAOInterface<cartItemBean, Long> {
    public cartItemDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public cartItemBean doRetrieveByKey(long id) throws SQLException {
        String query = "SELECT * FROM KartItem WHERE idItem = ?";
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
    public Collection<cartItemBean> doRetrieveAll(String order) throws SQLException {
        List<cartItemBean> cartItem = new ArrayList<>();
        String query = "SELECT * FROM KartItem";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cartItemBean orderItem = extractOrderItemFromResultSet(resultSet);
                cartItem.add(orderItem);
            }
        }
        return cartItem;
    }

    @Override
    public void doSave(cartItemBean orderItem) throws SQLException {
        String query = "INSERT INTO KartItem (IdOrdine, Prezzo) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderItem.getIdCarrello());
            statement.setLong(2, orderItem.getPrezzo());
            statement.executeUpdate();
        }
    }

    @Override
    public void doUpdate(cartItemBean orderItem) throws SQLException {
        String query = "UPDATE KartItem SET IdOrdine = ?, Prezzo = ? WHERE idItem = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, orderItem.getIdCarrello());
            statement.setLong(2, orderItem.getPrezzo());
            statement.setLong(4, orderItem.getIdItem());
            statement.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM KartItem WHERE idItem = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    private cartItemBean extractOrderItemFromResultSet(ResultSet resultSet) throws SQLException {
        cartItemBean cartItem = new cartItemBean();
        cartItem.setIdItem(resultSet.getLong("idItem"));
        cartItem.setIdCarrello(resultSet.getLong("IdOrdine"));
        cartItem.setPrezzo(resultSet.getLong("Prezzo"));
        return cartItem;
    }
}
