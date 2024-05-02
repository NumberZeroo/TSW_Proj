package model.carrello;

import model.ConnectionPool;
import model.DAOInterface;
import java.sql.*;
import java.util.*;

public class CarrelloDAO implements DAOInterface<CarrelloBean, Long> {
    private Connection connection;

    public CarrelloDAO() {
        this.connection = ConnectionPool.getConnection();
    }

    @Override
    public CarrelloBean doRetrieveByKey(int id) throws SQLException {
        String query = "SELECT * FROM Carrello WHERE IdUtente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractCarrelloFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Collection<CarrelloBean> doRetrieveAll(String order) throws SQLException {
        List<CarrelloBean> carrelli = new ArrayList<>();
        String query = "SELECT * FROM Carrello";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                CarrelloBean carrello = extractCarrelloFromResultSet(resultSet);
                carrelli.add(carrello);
            }
        }
        return carrelli;
    }

    @Override
    public void doSave(CarrelloBean carrello) throws SQLException {
        String query = "INSERT INTO Carrello (IdUtente, idProdotto, Quantita) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, carrello.getIdUtente());
            statement.setLong(2, carrello.getIdProdotto());
            statement.setLong(3, carrello.getQuantita());
            statement.executeUpdate();
        }
    }

    @Override
    public void doUpdate(CarrelloBean carrello) throws SQLException {
        String query = "UPDATE Carrello SET Quantita = ? WHERE IdUtente = ? AND idProdotto = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, carrello.getQuantita());
            statement.setLong(2, carrello.getIdUtente());
            statement.setLong(3, carrello.getIdProdotto());
            statement.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM Carrello WHERE IdUtente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    private CarrelloBean extractCarrelloFromResultSet(ResultSet resultSet) throws SQLException {
        CarrelloBean carrello = new CarrelloBean();
        carrello.setIdUtente(resultSet.getLong("IdUtente"));
        carrello.setIdProdotto(resultSet.getLong("idProdotto"));
        carrello.setQuantita(resultSet.getLong("Quantita"));
        return carrello;
    }

    public void close() {
        if (connection != null) {
            ConnectionPool.releaseConnection(connection);
            connection = null;
        }
    }
}