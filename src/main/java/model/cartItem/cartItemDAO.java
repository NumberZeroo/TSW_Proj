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
        String query = "SELECT * FROM CartItem WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractcartItemFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Collection<cartItemBean> doRetrieveAll(String order) throws SQLException {
        List<cartItemBean> cartItem = new ArrayList<>();
        String query = "SELECT * FROM CartItem";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cartItem.add(extractcartItemFromResultSet(resultSet));
            }
        }
        return cartItem;
    }

    @Override
    public void doSave(cartItemBean cartItem) throws SQLException {
        String query = "INSERT INTO CartItem (id) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartItem.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void doUpdate(cartItemBean cartItem) throws SQLException {
        String query = "UPDATE CartItem SET idProdotto = ?, idCarrello = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartItem.getIdProdotto());
            statement.setLong(2, cartItem.getIdCarrello());
            statement.setLong(3, cartItem.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM CartItem WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    private cartItemBean extractcartItemFromResultSet(ResultSet resultSet) throws SQLException {
        cartItemBean cartItem = new cartItemBean();
        cartItem.setId(resultSet.getLong("id"));
        cartItem.setIdProdotto(resultSet.getLong("idItem"));
        cartItem.setIdCarrello(resultSet.getLong("IdOrdine"));
        return cartItem;
    }
}
