package resumeapp.storage.dbbased;

import resumeapp.exception.NotExistStorageException;
import resumeapp.exception.StorageException;
import resumeapp.model.Resume;
import resumeapp.sql.ConnectionFactory;
import resumeapp.storage.Storage;

import java.sql.*;
import java.util.ArrayList;
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
             PreparedStatement statement = connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            statement.execute();
        } catch (SQLException sqle) {
            throw new StorageException("Could not clear table", sqle);
        }
    }

    @Override
    public void save(Resume resume) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
            statement.setString(1, resume.getUuid());
            statement.setString(2, resume.getFullName());
            statement.execute();
        } catch (SQLException sqle) {
            throw new StorageException("Could not get any data", sqle);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
            statement.setString(1, uuid);
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException sqle) {
            throw new StorageException("Could not get any data", sqle);
        }
    }

    @Override
    public void delete(String uuid) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT FROM resume WHERE uuid = ?")) {
            statement.setString(1, uuid);
            statement.execute();
        } catch (SQLException sqle) {
            throw new StorageException("Could not get any data", sqle);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM resume ORDER BY full_name, uuid")) {
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("Storage is empty");
            }
            List<Resume> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return result;
        } catch (SQLException sqle) {
            throw new StorageException("Could not get any data", sqle);
        }
    }

    @Override
    public int size() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT count(*) as count FROM resume ")) {
            ResultSet rs = statement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException("Storage is empty");
            }
            return rs.getInt("count");
        } catch (SQLException sqle) {
            throw new StorageException("Could not get any data", sqle);
        }
    }
}
