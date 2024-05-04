package model.ordine;

import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import model.DAOInterface;

import java.sql.*;
import java.util.*;

public class OrdineDAO extends AbstractDAO implements DAOInterface<OrdineBean, Long> {
    public OrdineDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public OrdineBean doRetrieveByKey(int id) throws SQLException {
        String query = "SELECT * FROM Ordine WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getOrder(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Collection<OrdineBean> doRetrieveAll(String order) throws SQLException {
        List<OrdineBean> ordini = new ArrayList<>();
        String query = "SELECT * FROM Ordine";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                OrdineBean ordine = getOrder(resultSet);
                ordini.add(ordine);
            }
        }
        return ordini;
    }

    @Override
    public void doSave(OrdineBean ordine) throws SQLException {
        String query = "INSERT INTO Ordine (idUtente, pathFattura) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, ordine.getIdUtente());
            statement.setString(2, ordine.getPathFattura());
            statement.executeUpdate();
        }
    }

    @Override
    public void doUpdate(OrdineBean ordine) throws SQLException {
        String query = "UPDATE Ordine SET idUtente = ?, pathFattura = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, ordine.getIdUtente());
            statement.setString(2, ordine.getPathFattura());
            statement.setLong(3, ordine.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM Ordine WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    private OrdineBean getOrder(ResultSet resultSet) throws SQLException {
        OrdineBean ordine = new OrdineBean();
        ordine.setId(resultSet.getLong("id"));
        ordine.setIdUtente(resultSet.getLong("idUtente"));
        ordine.setPathFattura(resultSet.getString("pathFattura"));
        return ordine;
    }
}
