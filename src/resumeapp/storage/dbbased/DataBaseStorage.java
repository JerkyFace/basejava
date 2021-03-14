package resumeapp.storage.dbbased;

import resumeapp.exception.NotExistStorageException;
import resumeapp.model.Resume;
import resumeapp.storage.Storage;
import resumeapp.util.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DataBaseStorage implements Storage {
    public final SqlHelper sqlHelper;

    public DataBaseStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume",
                "Could not clear table",
                PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?",
                "Could not update record",
                statement -> {
                    statement.setString(1, resume.getFullName());
                    statement.setString(2, resume.getUuid());
                    if (statement.executeUpdate() == 0) {
                        throw new NotExistStorageException(resume.getUuid());
                    }
                    return null;
                });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)",
                "Could not save any data",
                statement -> {
                    statement.setString(1, resume.getUuid());
                    statement.setString(2, resume.getFullName());
                    if (statement.executeUpdate() == 0) {
                        throw new NotExistStorageException(resume.getUuid());
                    }
                    return null;
                });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute("SELECT * FROM resume WHERE uuid = ?",
                "Could not get any data",
                statement -> {
                    statement.setString(1, uuid);
                    ResultSet rs = statement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(uuid, rs.getString("full_name"));
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?",
                "Could not delete data",
                statement -> {
                    statement.setString(1, uuid);
                    statement.execute();
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        return sqlHelper.execute("SELECT uuid, full_name FROM resume ORDER BY full_name",
                "Could not get any data",
                statement -> {
                    ResultSet rs = statement.executeQuery();
                    while (rs.next()) {
                        list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
                    }
                    return list;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) as count FROM resume",
                "Could not get any data",
                statement -> {
                    ResultSet rs = statement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException("Storage is empty");
                    }
                    return rs.getInt("count");
                });
    }
}
