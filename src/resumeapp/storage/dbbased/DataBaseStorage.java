package resumeapp.storage.dbbased;

import resumeapp.exception.StorageException;
import resumeapp.model.Resume;
import resumeapp.sql.ConnectionFactory;
import resumeapp.storage.Storage;

import java.sql.*;
import java.util.List;

public class DataBaseStorage implements Storage {
    public final ConnectionFactory connectionFactory;

    public DataBaseStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("DELETE FROM resume")) {
            statement.execute();
        } catch (SQLException sqle) {
            throw new StorageException("Could not clear table", sqle);
        }
    }

    @Override
    public void update(Resume resume) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("UPDATE resume SET fullname = ? WHERE uuid = ?")) {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            //todo;
        } catch (SQLException sqle) {
            throw new StorageException("Could not clear table", sqle);
        }
    }

    @Override
    public void save(Resume resume) {

    }

    @Override
    public Resume get(String uuid) {
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public List<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
