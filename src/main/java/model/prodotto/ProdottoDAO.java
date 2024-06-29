package model.prodotto;

import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import model.DAOInterface;

import java.sql.*;
import java.util.*;

public class ProdottoDAO extends AbstractDAO implements DAOInterface<ProdottoBean, Long> {
    public ProdottoDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public ProdottoBean doRetrieveByKey(long id) throws SQLException {
        String query = "SELECT * FROM Prodotto WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getProdotto(resultSet);
                }
            }
        }
        return null;
    }

    // Prende come parametri una mappa <id_prodotto, quantità> e ritorna una mappa <ProdottoBean, quantità>
    // Non posso fare un prepared statement contenente tutti gli id perché le query non vengono fatte in modo ordinato
    // TODO: Questo metodo fa tante query...c'è un modo migliore?
    public Map<ProdottoBean, Integer> doRetrieveByKeys(Map<Long, Integer> productsWithQuantity) throws SQLException {
        Map<ProdottoBean, Integer> prodottoBeans = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : productsWithQuantity.entrySet()) {
            ProdottoBean p = doRetrieveByKey(entry.getKey());
            if (p != null) {
                prodottoBeans.put(p, entry.getValue());
            }
        }
        return prodottoBeans;
    }

    public Collection<ProdottoBean> doRetrieveFiltered(String price, String size, String category, String animalRace, String sterilized, String minAge, String maxAge) throws SQLException {
    List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM Prodotto WHERE Prezzo <= ? AND Taglia = ? AND Categoria = ? AND TipoAnimale = ? AND Sterilizzati = ? AND MinEta >= ? AND MaxEta <= ?";

    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, Integer.parseInt(price));
        statement.setString(2, size);
        statement.setString(3, category);
        statement.setInt(4, Integer.parseInt(animalRace));
        statement.setInt(5, Integer.parseInt(sterilized));
        statement.setInt(6, Integer.parseInt(minAge));
        statement.setInt(7, Integer.parseInt(maxAge));

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ProdottoBean prodotto = getProdotto(resultSet);
                prodotti.add(prodotto);
            }
        }
    }
    return prodotti;
}

    @Override
    public Collection<ProdottoBean> doRetrieveAll(String order) throws SQLException {
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM Prodotto";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ProdottoBean prodotto = getProdotto(resultSet);
                prodotti.add(prodotto);
            }
        }
        return prodotti;
    }

    public Collection<ProdottoBean> doRetrieveAllByCategory(String category) throws SQLException {
        List<ProdottoBean> prodotti = new ArrayList<>();
        String query = "SELECT * FROM Prodotto WHERE Categoria = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProdottoBean prodotto = getProdotto(resultSet);
                    prodotti.add(prodotto);
                }
            }
        }

        return prodotti;
    }

    @Override
    public long doSave(ProdottoBean prodotto) throws SQLException {
        String query = "INSERT INTO Prodotto (Nome, Disponibilità, Taglia, Categoria, MinEta, MaxEta, IVA, Prezzo, Sterilizzati, imgPath, descrizione, TipoAnimale, visibile) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        long generatedKey = -1;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, prodotto.getNome());
            statement.setInt(2, prodotto.getDisponibilita());
            statement.setString(3, prodotto.getTaglia());
            statement.setString(4, prodotto.getCategoria());
            statement.setInt(5, prodotto.getMinEta());
            statement.setInt(6, prodotto.getMaxEta());
            statement.setString(7, prodotto.getIva());
            statement.setDouble(8, prodotto.getPrezzo());
            statement.setBoolean(9, prodotto.getSterilizzati());
            statement.setString(10, prodotto.getImgPath());
            statement.setString(11, prodotto.getDescrizione());
            statement.setString(12, String.valueOf(prodotto.getTipoAnimale()));
            statement.setInt(13, prodotto.isVisibile() ? 1 : 0);
            if (statement.executeUpdate() > 0){
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()){
                    generatedKey = rs.getLong(1);
                }
            }
        }
        return generatedKey;
    }

    @Override
    public void doUpdate(ProdottoBean prodotto) throws SQLException {

        String query = "UPDATE Prodotto SET Nome = ?, Disponibilità = ?, Taglia = ?, Categoria = ?, MinEta = ?, MaxEta = ?, IVA = ?, Prezzo = ?, Sterilizzati = ?, imgPath = ?, descrizione = ?, visibile = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, prodotto.getNome());
            statement.setInt(2, prodotto.getDisponibilita());
            statement.setString(3, prodotto.getTaglia());
            statement.setString(4, prodotto.getCategoria());
            statement.setInt(5, prodotto.getMinEta());
            statement.setInt(6, prodotto.getMaxEta());
            statement.setString(7, String.valueOf(prodotto.getIva()));
            statement.setDouble(8, prodotto.getPrezzo());
            statement.setBoolean(9, prodotto.getSterilizzati());
            statement.setString(10, prodotto.getImgPath());
            statement.setString(11, prodotto.getDescrizione());
            statement.setInt(12, prodotto.isVisibile() ? 1 : 0);
            statement.setLong(13, prodotto.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM Prodotto WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    private ProdottoBean getProdotto(ResultSet resultSet) throws SQLException {
        ProdottoBean prodotto = new ProdottoBean();
        prodotto.setId(resultSet.getLong("id"));
        prodotto.setNome(resultSet.getString("Nome"));
        prodotto.setDisponibilita(resultSet.getInt("Disponibilità"));
        prodotto.setTaglia(resultSet.getString("Taglia"));
        prodotto.setCategoria(resultSet.getString("Categoria"));
        prodotto.setMinEta(resultSet.getInt("MinEta"));
        prodotto.setMaxEta(resultSet.getInt("MaxEta"));
        prodotto.setIva(resultSet.getString("IVA"));
        prodotto.setPrezzo(resultSet.getLong("Prezzo"));
        prodotto.setSterilizzati(resultSet.getBoolean("Sterilizzati"));
        prodotto.setImgPath(resultSet.getString("imgPath"));
        prodotto.setTipoAnimale(resultSet.getInt("TipoAnimale"));
        prodotto.setDescrizione(resultSet.getString("descrizione"));
        prodotto.setVisibile(resultSet.getInt("visibile") == 1);
        return prodotto;
    }

    // Metodo per la ricerca di prodotti tramite query, todo: da implementare ajax
    public List<ProdottoBean> doRetrieveByQuery(String query) throws SQLException {
        List<ProdottoBean> prodotti = new ArrayList<>();
        String sql = "SELECT * FROM Prodotto WHERE Nome LIKE ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + query + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProdottoBean prodotto = getProdotto(resultSet);
                    prodotti.add(prodotto);
                }
            }
        }
        return prodotti;
    }

    public void changeVisibility(long id, boolean isVisible) throws SQLException {
    String query = "UPDATE Prodotto SET visibile = ? WHERE id = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, isVisible ? 1 : 0);
        statement.setLong(2, id);
        statement.executeUpdate();
    }
}
}