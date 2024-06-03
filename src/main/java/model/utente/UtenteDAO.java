package model.utente;
import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import model.DAOInterface;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class UtenteDAO extends AbstractDAO implements DAOInterface<UtenteBean, Long> {
    public UtenteDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public UtenteBean doRetrieveByKey(int id) throws SQLException {
        String query = "SELECT * FROM Utente WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUtente(resultSet);
                }
            }
        }
        return null;
    }

    public UtenteBean doRetrieveByUsername(String username) throws SQLException {
        String query = "SELECT * FROM Utente WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return getUtente(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Collection<UtenteBean> doRetrieveAll(String order) throws SQLException {
        List<UtenteBean> utenti = new ArrayList<>();
        String query = "SELECT * FROM Utente";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                UtenteBean utente = getUtente(resultSet);
                utenti.add(utente);
            }
        }
        return utenti;
    }

    public Optional<UtenteBean> doRetrieveByLogin(String username, String passwordHash) throws SQLException {
        String query = "SELECT * FROM Utente WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, passwordHash);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(getUtente(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    public void doSave(UtenteBean utente) throws SQLException {
        String query = "INSERT INTO Utente (username, email, imgPath, isAdmin, password) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, utente.getUsername());
            statement.setString(2, utente.getEmail());
            statement.setString(3, utente.getImgPath());
            statement.setLong(4, utente.getIsAdmin());
            statement.setString(5, utente.getPassword());
            statement.executeUpdate();
        }
    }

    @Override
    public void doUpdate(UtenteBean utente) throws SQLException {
        String query = "UPDATE Utente SET username = ?, email = ?, imgPath = ?, isAdmin = ?, password = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, utente.getUsername());
            statement.setString(2, utente.getEmail());
            statement.setString(3, utente.getImgPath());
            statement.setLong(4, utente.getIsAdmin());
            statement.setString(5, utente.getPassword());
            statement.setLong(6, utente.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(Long id) throws SQLException {
        String query = "DELETE FROM Utente WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    // Estrae un oggetto UtenteBean da un ResultSet
    private UtenteBean getUtente(ResultSet resultSet) throws SQLException {
        UtenteBean utente = new UtenteBean();
        utente.setId(resultSet.getLong("id"));
        utente.setUsername(resultSet.getString("username"));
        utente.setEmail(resultSet.getString("email"));
        utente.setImgPath(resultSet.getString("imgPath"));
        utente.setIsAdmin(resultSet.getLong("isAdmin"));
        utente.setPassword(resultSet.getString("password"));
        return utente;
    }
}
