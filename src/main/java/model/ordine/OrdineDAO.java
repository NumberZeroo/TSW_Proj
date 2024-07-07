package model.ordine;

import com.tswproject.tswproj.EmptyPoolException;
import jakarta.servlet.annotation.MultipartConfig;
import model.AbstractDAO;
import model.DAOInterface;

import java.sql.*;
import java.sql.Date;
import java.util.*;

@MultipartConfig
public class OrdineDAO extends AbstractDAO implements DAOInterface<OrdineBean, Long> {
    public OrdineDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public OrdineBean doRetrieveByKey(long id) throws SQLException {
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
    public long doSave(OrdineBean ordine) throws SQLException {
        String query = "INSERT INTO Ordine (idUtente, infoConsegna) VALUES (?, ?)";
        long generatedKey = -1;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setLong(1, ordine.getIdUtente());
            statement.setLong(2, ordine.getIdInfoConsegna());
            if (statement.executeUpdate() > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedKey = resultSet.getLong(1);
                }
            }
        }
        return generatedKey;
    }

    @Override
    public void doUpdate(OrdineBean ordine) throws SQLException {
        String query = "UPDATE Ordine SET idUtente = ?, infoConsegna = ?, dataOrdine = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, ordine.getIdUtente());
            statement.setLong(2, ordine.getIdInfoConsegna());
            statement.setDate(3, ordine.getData());
            statement.setLong(4, ordine.getId());
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
        ordine.setIdInfoConsegna(resultSet.getLong("infoConsegna"));
        ordine.setData(resultSet.getDate("dataOrdine"));
        return ordine;
    }

    public boolean hasUserPurchasedProduct(long userId, long productId) throws SQLException {
        String query = "SELECT COUNT(*) FROM Ordine JOIN OrderItem ON Ordine.id = OrderItem.IdOrdine WHERE Ordine.idUtente = ? AND OrderItem.idProdotto = ?";
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

    public Collection<OrdineBean> doRetrieveByUser(long userId) throws SQLException {
        List<OrdineBean> ordini = new ArrayList<>();
        String query = "SELECT * FROM Ordine WHERE idUtente = ? ORDER BY id DESC";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    OrdineBean ordine = getOrder(resultSet);
                    ordini.add(ordine);
                }
            }
        }
        return ordini;
    }

    public Collection<OrdineBean> doRetrieveByDate(String startDate, String endDate) throws SQLException {
        List<OrdineBean> ordini = new ArrayList<>();
        String query;
        PreparedStatement statement;

        if (!startDate.isEmpty() && !endDate.isEmpty()) {
            Date data1 = Date.valueOf(startDate);
            Date data2 = Date.valueOf(endDate);

            query = "SELECT * FROM Ordine WHERE dataOrdine BETWEEN ? AND ?";
            statement = connection.prepareStatement(query);
            statement.setDate(1, (data1));
            statement.setDate(2, (data2));

        } else if (!startDate.isEmpty()) {

            Date data1 = Date.valueOf(startDate);
            query = "SELECT * FROM Ordine WHERE dataOrdine >= ?";
            statement = connection.prepareStatement(query);
            statement.setDate(1, (data1));

        } else if (!endDate.isEmpty()) {

            Date data2 = Date.valueOf(endDate);
            query = "SELECT * FROM Ordine WHERE dataOrdine <= ?";
            statement = connection.prepareStatement(query);
            statement.setDate(1, (data2));

        } else {
            return doRetrieveAll("");
        }

        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                OrdineBean ordine = getOrder(resultSet);
                ordini.add(ordine);
            }
        }

        return ordini;
    }
}
