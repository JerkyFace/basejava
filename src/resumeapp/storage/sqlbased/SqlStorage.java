package resumeapp.storage.sqlbased;

import resumeapp.exception.NotExistStorageException;
import resumeapp.model.ContactType;
import resumeapp.model.Resume;
import resumeapp.storage.Storage;
import resumeapp.util.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume",
                PreparedStatement::execute);
    }

    @Override
    public void update(Resume resume) {
        String uuid = resume.getUuid();
        sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?",
                statement -> {
                    statement.setString(1, resume.getFullName());
                    statement.setString(2, uuid);
                    if (statement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
        sqlHelper.execute("DELETE FROM contact WHERE resume_uuid = ?",
                statement -> {
                    statement.setString(1, uuid);
                    statement.execute();
                    return null;
                });
        sqlHelper.execute("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?);",
                statement -> {
                    for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                        statement.setString(2, e.getKey().name());
                        statement.setString(3, e.getValue());
                        statement.setString(1, uuid);
                        statement.addBatch();
                    }
                    statement.executeBatch();
                    return null;
                });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                    statement.setString(1, resume.getUuid());
                    statement.setString(2, e.getKey().name());
                    statement.setString(3, e.getValue());
                    statement.addBatch();
                }
                statement.executeBatch();
            }

            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(" SELECT * FROM resume r" +
                        " LEFT JOIN contact c" +
                        " ON r.uuid = c.resume_uuid" +
                        " WHERE r.uuid = ?",
                statement -> {
                    statement.setString(1, uuid);
                    ResultSet rs = statement.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    do {
                        resume.addContact(ContactType.valueOf(rs.getString("type")),
                                rs.getString("value"));
                    }
                    while (rs.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?",
                statement -> {
                    statement.setString(1, uuid);
                    if (statement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute(" SELECT * FROM resume r" +
                        " LEFT JOIN contact c" +
                        " ON r.uuid = c.resume_uuid " +
                        " ORDER BY full_name, uuid",
                statement -> {
                    ResultSet rs = statement.executeQuery();
                    Map<String, Resume> resumes = new LinkedHashMap<>();
                    while (rs.next()) {
                        String uuid = rs.getString("uuid");
                        Resume resume = new Resume(uuid, rs.getString("full_name"));
                        resumes.putIfAbsent(uuid, resume);
                        resumes.get(uuid).addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                    }
                    return new ArrayList<>(resumes.values());
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute("SELECT count(*) as count FROM resume",
                statement -> {
                    ResultSet rs = statement.executeQuery();
                    rs.next();
                    return rs.getInt("count");
                });
    }
}
