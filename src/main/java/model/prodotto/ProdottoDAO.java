package model.prodotto;

import com.tswproject.tswproj.EmptyPoolException;
import model.AbstractDAO;
import com.tswproject.tswproj.ConnectionPool;
import model.DAOInterface;

import javax.swing.*;
import java.sql.*;
import java.util.*;

public class ProdottoDAO extends AbstractDAO implements DAOInterface<ProdottoBean, Long> {
    public ProdottoDAO() throws EmptyPoolException {
        super();
    }

    @Override
    public ProdottoBean doRetrieveByKey(int id) throws SQLException {
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
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "mammt:");
            e.printStackTrace();
        }
        return prodotti;
    }

    @Override
    public void doSave(ProdottoBean prodotto) throws SQLException {
        String query = "INSERT INTO Prodotto (Nome, Disponibilità, Taglia, Tipo, MinEta, MaxEta, IVA, Prezzo, Sterilizzati, imgPath) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, prodotto.getNome());
            statement.setInt(2, prodotto.getDisponibilita());
            statement.setString(3, prodotto.getTaglia());
            statement.setString(4, prodotto.getTipo());
            statement.setInt(5, prodotto.getMinEta());
            statement.setInt(6, prodotto.getMaxEta());
            statement.setInt(7, prodotto.getIva());
            statement.setLong(8, prodotto.getPrezzo());
            statement.setBoolean(9, prodotto.getSterilizzati());
            statement.setString(10, prodotto.getImgPath());
            statement.executeUpdate();
        }
    }

    @Override
    public void doUpdate(ProdottoBean prodotto) throws SQLException {
        String query = "UPDATE Prodotto SET Nome = ?, Disponibilità = ?, Taglia = ?, Tipo = ?, MinEta = ?, MaxEta = ?, IVA = ?, Prezzo = ?, Sterilizzati = ?, imgPath = ? WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, prodotto.getNome());
            statement.setInt(2, prodotto.getDisponibilita());
            statement.setString(3, prodotto.getTaglia());
            statement.setString(4, prodotto.getTipo());
            statement.setInt(5, prodotto.getMinEta());
            statement.setInt(6, prodotto.getMaxEta());
            statement.setInt(7, prodotto.getIva());
            statement.setLong(8, prodotto.getPrezzo());
            statement.setBoolean(9, prodotto.getSterilizzati());
            statement.setString(10, prodotto.getImgPath());
            statement.setLong(11, prodotto.getId());
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
        prodotto.setTipo(resultSet.getString("Tipo"));
        prodotto.setMinEta(resultSet.getInt("MinEta"));
        prodotto.setMaxEta(resultSet.getInt("MaxEta"));
        prodotto.setIva(Integer.parseInt(resultSet.getString("IVA")));
        prodotto.setPrezzo(resultSet.getLong("Prezzo"));
        prodotto.setSterilizzati(resultSet.getBoolean("Sterilizzati"));
        prodotto.setImgPath(resultSet.getString("imgPath"));
        return prodotto;
    }
}