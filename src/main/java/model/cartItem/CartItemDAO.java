package model.cartItem;

import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import model.DAOInterface;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CartItemDAO extends AbstractDAO implements DAOInterface<CartItemBean, Long> {
    public CartItemDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public CartItemBean doRetrieveByKey(long id) throws SQLException {
        String query = "SELECT * FROM CartItem WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractCartItemFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Collection<CartItemBean> doRetrieveAll(String order) throws SQLException {
        List<CartItemBean> cartItem = new ArrayList<>();
        String query = "SELECT * FROM CartItem";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cartItem.add(extractCartItemFromResultSet(resultSet));
            }
        }
        return cartItem;
    }

    @Override
    public void doSave(CartItemBean cartItem) throws SQLException {
        String query = "INSERT INTO CartItem (id) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartItem.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void doUpdate(CartItemBean cartItem) throws SQLException {
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

    /**
     * Aggiunge un prodotto (identificato da productId) al carrello (cartId) con una certa quantità
     * Se esiste già aumenta la quantitò
     * @param productId     id prodotto da aggiungere
     * @param cartId        id carrello
     * @param quantity      quantità (da sommare se productId esiste giò nel carrello)
     * @throws SQLException per via del prepared statement
     */
    public void addProduct(long productId, long cartId, int quantity) throws SQLException {
        // 1. Seleziona productId da cartId
        String query1 = "SELECT * FROM CartItem WHERE idCarrello = ? and idProdotto = ?";
        boolean found = false;
        try(PreparedStatement statement = connection.prepareStatement(query1)) {
            statement.setLong(1, cartId);
            statement.setLong(2, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    quantity += resultSet.getInt("quantita");
                    found = true;
                }
            }
        }

        // 2. Se esiste aggiorna, altrimenti crea prodotto
        if (found){
            String query = "UPDATE CartItem SET quantita = ? WHERE idCarrello = ? AND idProdotto = ?";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setInt(1, quantity);
                statement.setLong(2, cartId);
                statement.setLong(3, productId);
                statement.executeUpdate();
            }
        }else {
            String query = "INSERT INTO CartItem (idProdotto, idCarrello, quantita) VALUES (?, ?, ?)";
            try (PreparedStatement statement = connection.prepareStatement(query)) {
                statement.setLong(1, productId);
                statement.setLong(2, cartId);
                statement.setInt(3, quantity);
                statement.executeUpdate();
            }
        }
    }

    /**
     * Rimuove dal carrello (cartId) "quantity" prodotti (productId)
     * @param productId         id prodotto da rimuovere
     * @param cartId            id carrello appartenente all'utente
     * @param quantity          quantità da rimuovere, se maggiore di quella nel db elimina il record
     * @throws SQLException     per il prepared statement
     */
    public void removeProduct(long productId, long cartId, int quantity) throws SQLException {
        // 1. Seleziona il prodotto
        String query1 = "SELECT * FROM CartItem WHERE idCarrello = ? and idProdotto = ?";
        boolean found = false;
        try(PreparedStatement statement = connection.prepareStatement(query1)) {
            statement.setLong(1, cartId);
            statement.setLong(2, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    quantity = resultSet.getInt("quantita") - quantity;
                    found = true;
                }
            }
        }
        if (found){
            if (quantity == 0){
                this.removeProduct(productId, cartId);
                return;
            }
            String query2 = "UPDATE CartItem SET quantita = ? WHERE idCarrello = ? AND idProdotto = ?";
            try (PreparedStatement statement = connection.prepareStatement(query2)) {
                statement.setInt(1, quantity);
                statement.setLong(2, cartId);
                statement.setLong(3, productId);
                statement.executeUpdate();
            }
        }
    }

    public void removeProduct(long productId, long cartId) throws SQLException {
        String query = "DELETE FROM CartItem WHERE idCarrello = ? AND idProdotto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartId);
            statement.setLong(2, productId);
            statement.executeUpdate();
        }
    }

    public Map<Long, Integer> getCartItems(long cartId) throws SQLException {
        String query = "SELECT * FROM CartItem WHERE idCarrello = ?";
        Map<Long, Integer> cartItem = new HashMap<>();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, cartId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    cartItem.put(resultSet.getLong("idProdotto"), (int) resultSet.getLong("quantita")); // TODO: cambia ad int nel db
                }
            }
        }
        return cartItem;
    }

    private CartItemBean extractCartItemFromResultSet(ResultSet resultSet) throws SQLException {
        CartItemBean cartItem = new CartItemBean();
        cartItem.setId(resultSet.getLong("id"));
        cartItem.setIdProdotto(resultSet.getLong("idItem"));
        cartItem.setIdCarrello(resultSet.getLong("IdOrdine"));
        return cartItem;
    }
}
