package model.wishlist;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import model.DAOInterface;

public class WishlistDAO extends AbstractDAO implements DAOInterface<WishlistBean, Long> {

    public WishlistDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public WishlistBean doRetrieveByKey(long id) throws SQLException {
        String query = "SELECT * FROM Wishlist WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getWishlist(resultSet);
                }
            }
        }
        return null;
    }

    public WishlistBean doRetrieveByUser(long id) throws SQLException {
        String query = "SELECT * FROM Wishlist WHERE idUtente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getWishlist(resultSet);
                }
            }
        }
        return null;
    }

    private WishlistBean getWishlist(ResultSet resultSet) throws SQLException {
        WishlistBean wishlist = new WishlistBean();
        wishlist.setId(resultSet.getLong("id"));
        wishlist.setUserId(resultSet.getLong("idUtente"));
        return wishlist;
    }

    @Override
    public Collection<WishlistBean> doRetrieveAll(String order) throws SQLException {
        return List.of();
    }

    @Override
    public long doSave(WishlistBean product) throws SQLException {
        return 0;
    }

    @Override
    public void doUpdate(WishlistBean product) throws SQLException {
    }

    @Override
    public boolean doDelete(Long code) throws SQLException {
        return false;
    }
}