//Interfaccia DAO da implementare nelle DAO dei beans
package model;

import java.sql.SQLException;
import java.util.Collection;

public interface DAOInterface<T> {
    public T doRetriveByKey(int code) throws SQLException;
    public Collection<T> doRetriveAll(String order) throws SQLException;
    public void doSave(T product) throws SQLException;
    void doUpdate(T product) throws SQLException;
    boolean doDelete(int code) throws SQLException;
}