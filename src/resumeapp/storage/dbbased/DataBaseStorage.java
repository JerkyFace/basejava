package resumeapp.storage.dbbased;

import org.postgresql.util.PSQLException;
import resumeapp.exception.ExistStorageException;
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
             PreparedStatement statement =
                     connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
            statement.setString(1, resume.getFullName());
            statement.setString(2, resume.getUuid());
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
        } catch (SQLException sqle) {
            throw new StorageException("Could not update record", sqle);
        }
    }

    @Override
    public void save(Resume resume) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
            statement.setString(1, resume.getUuid());
            statement.setString(2, resume.getFullName());
            statement.execute();
        } catch (Exception e) {
            if (e instanceof PSQLException && ((PSQLException) e).getSQLState().equals("23505")) {
                throw new ExistStorageException(resume.getUuid());
            }
            throw new StorageException("Could not save any data", e);
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
             PreparedStatement statement = connection.prepareStatement("DELETE FROM resume WHERE uuid = ?")) {
            statement.setString(1, uuid);
            statement.execute();
        } catch (SQLException sqle) {
            throw new StorageException("Could not delete any data", sqle);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement("SELECT uuid, full_name FROM resume ORDER BY full_name")) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return list;
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
