package model.resolvedCartItem;

import model.AbstractDAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * N.B. a differenza delle altre DAO questa non implementa la DAOInterface perché non ha una effettiva persistenza sul db
 */
public class ResolvedCartItemDAO extends AbstractDAO {

    /**
     * Dato un "productId" ed un "cartId" fa l'associazione prodotto-item nel carrello
     * @param productId id prodotto
     * @param cartId    id carrello
     * @return          ResolvedCartItemBean
     */
    public ResolvedCartItemBean doRetrieveByProductId(long productId, long cartId) throws SQLException {
        String query = "SELECT Prodotto.id AS \"idProdotto\", CartItem.IdCarrello, CartItem.id AS \"idCartItem\", CartItem.Quantita, Prodotto.Categoria, Prodotto.Descrizione, Prodotto.Disponibilità, Prodotto.imgPath, Prodotto.IVA, Prodotto.MaxEta, Prodotto.MinEta, Prodotto.Nome, Prodotto.Prezzo, Prodotto.Sterilizzati, Prodotto.Taglia, Prodotto.TipoAnimale, Prodotto.Visibile  FROM CartItem JOIN Prodotto ON Prodotto.id = CartItem.idProdotto WHERE CartItem.idCarrello= ? AND Prodotto.id = ?";
        try(PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setLong(1, cartId);
            pstmt.setLong(2, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return getResolvedCartItemFromRS(rs);
            }
            return null;
        }
    }

    public Map<ResolvedCartItemBean, Integer> doRetrieveByProductIds(Map<Long, Integer> productIds, long cartId) throws SQLException {
        Map<ResolvedCartItemBean, Integer> cartItems = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : productIds.entrySet()) {
            ResolvedCartItemBean res = doRetrieveByProductId(entry.getKey(), cartId);
            if (res != null) {
                cartItems.put(res, entry.getValue());
            }
        }
        return cartItems;
    }

    private ResolvedCartItemBean getResolvedCartItemFromRS(ResultSet rs) throws SQLException {
        ResolvedCartItemBean resolvedCartItemBean = new ResolvedCartItemBean();

        resolvedCartItemBean.setTipoAnimale(rs.getInt("TipoAnimale"));
        resolvedCartItemBean.setVisibile(rs.getBoolean("Visibile"));
        resolvedCartItemBean.setNome(rs.getString("Nome"));
        resolvedCartItemBean.setDisponibilita(rs.getInt("Disponibilità"));
        resolvedCartItemBean.setTaglia(rs.getString("Taglia"));
        resolvedCartItemBean.setCategoria(rs.getString("Categoria"));
        resolvedCartItemBean.setMinEta(rs.getInt("MinEta"));
        resolvedCartItemBean.setMaxEta(rs.getInt("MaxEta"));
        resolvedCartItemBean.setIva(rs.getString("Iva"));
        resolvedCartItemBean.setPrezzo(rs.getDouble("Prezzo"));
        resolvedCartItemBean.setSterilizzati(rs.getBoolean("Sterilizzati"));
        resolvedCartItemBean.setImgPath(rs.getString("imgPath"));
        resolvedCartItemBean.setDescrizione(rs.getString("Descrizione"));
        resolvedCartItemBean.setIdProdotto(rs.getLong("idProdotto"));
        resolvedCartItemBean.setIdCartItem(rs.getLong("idCartItem"));
        resolvedCartItemBean.setIdCarrello(rs.getLong("idCarrello"));
        resolvedCartItemBean.setQuantita(rs.getInt("Quantita"));

        return resolvedCartItemBean;
    }
}
