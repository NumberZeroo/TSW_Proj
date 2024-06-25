package model.pet;

import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import model.DAOInterface;
import java.sql.*;
import java.util.*;

public class PetDAO extends AbstractDAO implements DAOInterface<PetBean, String> {
    public PetDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public PetBean doRetrieveByKey(long id) throws SQLException {
        String query = "SELECT * FROM Pet WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
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
    public long doSave(PetBean pet) throws SQLException {
        String query = "INSERT INTO Pet (Nome, IdUtente, imgPath, Tipo, Taglia, Sterilizzato, DataNascita) VALUES (?, ?, ?, ?, ?, ?, ?)";
        long generatedKey = -1;
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, pet.getNome());
            statement.setLong(2, pet.getIdUtente());
            statement.setString(3, pet.getImgPath());
            statement.setString(4, pet.getTipo());
            statement.setString(5, pet.getTaglia());
            statement.setInt(6, pet.getSterilizzato() ? 1 : 0);
            statement.setDate(7, pet.getDataNascita());
            if (statement.executeUpdate() > 0){
                ResultSet resultSet = statement.getGeneratedKeys();
                if (resultSet.next()){
                    generatedKey = resultSet.getLong(1);
                }
            }
        }
        return generatedKey;
    }

    @Override
    public void doUpdate(PetBean pet) throws SQLException {
        String query = "UPDATE Pet SET IdUtente = ?, imgPath = ?, Tipo = ?, Taglia = ?, Sterilizzato = ?, DataNascita = ? WHERE Nome = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, pet.getIdUtente());
            statement.setString(2, pet.getImgPath());
            statement.setString(3, pet.getTipo());
            statement.setString(4, pet.getTaglia());
            statement.setInt(5, pet.getSterilizzato() ? 1 : 0);
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
        pet.setSterilizzato(resultSet.getInt("Sterilizzato") == 1);
        pet.setDataNascita(resultSet.getDate("DataNascita"));
        return pet;
    }

    public List<PetBean> doRetrieveByUser(long id) throws SQLException {
        List<PetBean> pets = new ArrayList<>();
        String query = "SELECT * FROM Pet WHERE IdUtente = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PetBean pet = extractPetFromResultSet(resultSet);
                    pets.add(pet);
                }
            }
        }
        return pets;
    }
}
