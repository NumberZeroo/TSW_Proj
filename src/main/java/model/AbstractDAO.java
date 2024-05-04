package model;

import com.tswproject.tswproj.ConnectionPool;
import com.tswproject.tswproj.EmptyPoolException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class AbstractDAO {
    protected Connection connection;

    public AbstractDAO() throws EmptyPoolException {
        this.connection = ConnectionPool.getConnection();
    }

    public void close() {
        if (connection != null) {
            ConnectionPool.releaseConnection(connection);
        }
    }
}
