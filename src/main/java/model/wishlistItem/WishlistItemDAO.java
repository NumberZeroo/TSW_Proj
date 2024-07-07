package model.wishlistItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import model.DAOInterface;
import model.wishlist.WishlistBean;

public class WishlistItemDAO extends AbstractDAO implements DAOInterface<WishlistItemBean, Long> {

    public WishlistItemDAO() throws EmptyPoolException {
        super();
    }

    public List<WishlistItemBean> doRetrieveByWishlistId(long wishlistId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT * FROM WishlistItem WHERE IdWishlist = ?");
        ) {
            ps.setLong(1, wishlistId);

            ArrayList<WishlistItemBean> items = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                WishlistItemBean item = new WishlistItemBean();
                item.setId(rs.getLong("id"));
                item.setWishlistId(rs.getLong("IdWishlist"));
                item.setProductId(rs.getLong("idProdotto"));
                items.add(item);
            }
            return items;
        }
    }

    @Override
    public WishlistItemBean doRetrieveByKey(long code) throws SQLException {
        return null;
    }

    @Override
    public Collection<WishlistItemBean> doRetrieveAll(String order) throws SQLException {
        return List.of();
    }

@Override
public long doSave(WishlistItemBean wishlistItem) throws SQLException {
    String query = "INSERT INTO WishlistItem (IdWishlist, idProdotto) VALUES (?, ?)";
    long generatedKey = -1;
    try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
        statement.setLong(1, wishlistItem.getWishlistId());
        statement.setLong(2, wishlistItem.getProductId());
        if (statement.executeUpdate() > 0){
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()){
                generatedKey = resultSet.getLong(1);
            }
        }
    }
    return generatedKey;
}

    @Override
    public void doUpdate(WishlistItemBean product) throws SQLException {

    }

    @Override
    public boolean doDelete(Long code) throws SQLException {
        return false;
    }

    public boolean doDelete(long wishlistId, long productId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(
                "DELETE FROM WishlistItem WHERE IdWishlist = ? AND idProdotto = ?")) {
            ps.setLong(1, wishlistId);
            ps.setLong(2, productId);
            return ps.executeUpdate() > 0;
        }
    }
}