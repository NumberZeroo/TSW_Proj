package model.pet;

import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import model.DAOInterface;
import com.tswproject.tswproj.ConnectionPool;
import java.sql.*;
import java.util.*;
public class PetDAO extends AbstractDAO implements DAOInterface<PetBean, String> {
    public PetDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public PetBean doRetrieveByKey(int id) throws SQLException {
        String query = "SELECT * FROM Pet WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return extractPetFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public Collection<PetBean> doRetrieveAll(String order) throws SQLException {
        List<PetBean> pets = new ArrayList<>();
        String query = "SELECT * FROM Pet";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                PetBean pet = extractPetFromResultSet(resultSet);
                pets.add(pet);
            }
        }
        return pets;
    }

    @Override
    public void doSave(PetBean pet) throws SQLException {
        String query = "INSERT INTO Pet (Nome, IdUtente, imgPath, Tipo, Taglia, Sterilizzato, DataNascita) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, pet.getNome());
            statement.setLong(2, pet.getIdUtente());
            statement.setString(3, pet.getImgPath());
            statement.setString(4, pet.getTipo());
            statement.setString(5, pet.getTaglia());
            statement.setString(6, pet.getSterilizzato());
            statement.setDate(7, pet.getDataNascita());
            statement.executeUpdate();
        }
    }

    @Override
    public void doUpdate(PetBean pet) throws SQLException {
        String query = "UPDATE Pet SET IdUtente = ?, imgPath = ?, Tipo = ?, Taglia = ?, Sterilizzato = ?, DataNascita = ? WHERE Nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, pet.getIdUtente());
            statement.setString(2, pet.getImgPath());
            statement.setString(3, pet.getTipo());
            statement.setString(4, pet.getTaglia());
            statement.setString(5, pet.getSterilizzato());
            statement.setDate(6, pet.getDataNascita());
            statement.setString(7, pet.getNome());
            statement.executeUpdate();
        }
    }

    @Override
    public boolean doDelete(String nome) throws SQLException {
        String query = "DELETE FROM Pet WHERE Nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, nome);
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        }
    }

    private PetBean extractPetFromResultSet(ResultSet resultSet) throws SQLException {
        PetBean pet = new PetBean();
        pet.setNome(resultSet.getString("Nome"));
        pet.setIdUtente(resultSet.getLong("IdUtente"));
        pet.setImgPath(resultSet.getString("imgPath"));
        pet.setTipo(resultSet.getString("Tipo"));
        pet.setTaglia(resultSet.getString("Taglia"));
        pet.setSterilizzato(resultSet.getString("Sterilizzato"));
        pet.setDataNascita(resultSet.getDate("DataNascita"));
        return pet;
    }
}
