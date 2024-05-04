package model.recensione;

import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import com.tswproject.tswproj.ConnectionPool;
import model.DAOInterface;

import java.sql.*;
import java.util.*;

public class RecensioneDAO extends AbstractDAO implements DAOInterface<RecensioneBean, Long> {
    public RecensioneDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public RecensioneBean doRetrieveByKey(int id) throws SQLException {
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

    @Override
    public void doSave(RecensioneBean recensione) throws SQLException {
        String query = "INSERT INTO Recensione (idUtente, Titolo, Commento, Valutazione, Data, idProdotto) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, recensione.getIdUtente());
            statement.setString(2, recensione.getTitolo());
            statement.setString(3, recensione.getCommento());
            statement.setLong(4, recensione.getValutazione());
            statement.setDate(5, recensione.getData());
            statement.setLong(6, recensione.getIdProdotto());
            statement.executeUpdate();
        }
    }

    @Override
    public void doUpdate(RecensioneBean recensione) throws SQLException {
        String query = "UPDATE Recensione SET idUtente = ?, Titolo = ?, Commento = ?, Valutazione = ?, Data = ?, idProdotto = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, recensione.getIdUtente());
            statement.setString(2, recensione.getTitolo());
            statement.setString(3, recensione.getCommento());
            statement.setLong(4, recensione.getValutazione());
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
        recensione.setValutazione(resultSet.getLong("Valutazione"));
        recensione.setData(resultSet.getDate("Data"));
        recensione.setIdProdotto(resultSet.getLong("idProdotto"));
        return recensione;
    }
}