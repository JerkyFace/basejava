package resumeapp.util.sql;

import org.postgresql.util.PSQLException;
import resumeapp.exception.ExistStorageException;
import resumeapp.exception.StorageException;
import resumeapp.sql.ConnectionFactory;
import resumeapp.sql.QueryExecutor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T execute(String query, QueryExecutor<T> executor) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            return executor.execute(statement);
        } catch (SQLException e) {
            if (e instanceof PSQLException && (e).getSQLState().equals("23505")) {
                throw new ExistStorageException();
            }
            throw new StorageException(e.getMessage());
        }
    }
}
