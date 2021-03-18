package resumeapp.storage.sqlbased;

import resumeapp.exception.NotExistStorageException;
import resumeapp.model.ContactType;
import resumeapp.model.Resume;
import resumeapp.storage.Storage;
import resumeapp.util.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?",
                statement -> {
                    String uuid = resume.getUuid();
                    statement.setString(1, resume.getFullName());
                    statement.setString(2, uuid);
                    if (statement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)",
                statement -> {
                    statement.setString(1, resume.getUuid());
                    statement.setString(2, resume.getFullName());
                    statement.execute();
                    return null;
                });
        for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
            sqlHelper.execute("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)",
                    statement -> {
                        statement.setString(1, resume.getUuid());
                        statement.setString(2, e.getKey().name());
                        statement.setString(3, e.getValue());
                        statement.execute();
                        return null;
                    });
        }
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
        //todo
        return sqlHelper.execute(" SELECT uuid, full_name FROM resume r" +
                        " LEFT JOIN contact c" +
                        " ON r.uuid = c.resume_uuid " +
                        " ORDER BY full_name, uuid",
                statement -> {
                    ResultSet rs = statement.executeQuery();
                    List<Resume> list = new ArrayList<>();
                    while (rs.next()) {
                        Resume resume = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                        resume.addContact(ContactType.valueOf(rs.getString("type")), rs.getString("value"));
                        list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
                    }
                    return list;
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
