package model.recensione;

import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import model.DAOInterface;

import java.sql.*;
import java.util.*;

public class RecensioneDAO extends AbstractDAO implements DAOInterface<RecensioneBean, Long> {
    public RecensioneDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public RecensioneBean doRetrieveByKey(long id) throws SQLException {
        String query = "SELECT * FROM Recensione WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getRecensione(resultSet);
                }
            }
        }
        return null;
    }

    // TODO: implement sorting output (order)
    @Override
    public Collection<RecensioneBean> doRetrieveAll(String order) throws SQLException {
        List<RecensioneBean> recensioni = new ArrayList<>();
        String query = "SELECT * FROM Recensione";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                RecensioneBean recensione = getRecensione(resultSet);
                recensioni.add(recensione);
            }
        }
        return recensioni;
    }

    public Collection<RecensioneBean> doRetrieveByProduct(long idProdotto) throws SQLException {
        List<RecensioneBean> recensioni = new ArrayList<>();
        String query = "SELECT * FROM Recensione WHERE idProdotto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, idProdotto);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    RecensioneBean recensione = getRecensione(resultSet);
                    recensioni.add(recensione);
                }
            }
        }
        return recensioni;
    }

    @Override
    public long doSave(RecensioneBean recensione) throws SQLException {
        String query = "INSERT INTO Recensione (idUtente, Titolo, Commento, Valutazione, Data, idProdotto) VALUES (?, ?, ?, ?, ?, ?)";
        long generatedKey = -1;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, recensione.getIdUtente());
            statement.setString(2, recensione.getTitolo());
            statement.setString(3, recensione.getCommento());
            statement.setDouble(4, recensione.getValutazione());
            statement.setDate(5, recensione.getData());
            statement.setLong(6, recensione.getIdProdotto());
            if (statement.executeUpdate() > 0) {
                ResultSet rs = statement.getGeneratedKeys();
                if (rs.next()) {
                    generatedKey = rs.getLong(1);
                }
            }
        }
        return generatedKey;
    }

    @Override
    public void doUpdate(RecensioneBean recensione) throws SQLException {
        String query = "UPDATE Recensione SET idUtente = ?, Titolo = ?, Commento = ?, Valutazione = ?, Data = ?, idProdotto = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, recensione.getIdUtente());
            statement.setString(2, recensione.getTitolo());
            statement.setString(3, recensione.getCommento());
            statement.setDouble(4, recensione.getValutazione());
            statement.setDate(5, recensione.getData());
            statement.setLong(6, recensione.getIdProdotto());
            statement.setLong(7, recensione.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM Recensione WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    private RecensioneBean getRecensione(ResultSet resultSet) throws SQLException {
        RecensioneBean recensione = new RecensioneBean();
        recensione.setId(resultSet.getLong("id"));
        recensione.setIdUtente(resultSet.getLong("idUtente"));
        recensione.setTitolo(resultSet.getString("Titolo"));
        recensione.setCommento(resultSet.getString("Commento"));
        recensione.setValutazione(resultSet.getDouble("Valutazione"));
        recensione.setData(resultSet.getDate("Data"));
        recensione.setIdProdotto(resultSet.getLong("idProdotto"));
        return recensione;
    }

    public boolean hasUserReviewedProduct(long userId, long productId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Recensione WHERE idUtente = ? AND idProdotto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            statement.setLong(2, productId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }
        }
        return false;
    }
}