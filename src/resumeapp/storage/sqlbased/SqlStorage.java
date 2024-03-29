package resumeapp.storage.sqlbased;

import resumeapp.exception.NotExistStorageException;
import resumeapp.model.AbstractSection;
import resumeapp.model.ContactType;
import resumeapp.model.Resume;
import resumeapp.model.SectionType;
import resumeapp.storage.Storage;
import resumeapp.util.JsonParser;
import resumeapp.util.sql.SqlHelper;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException(e);
        }
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
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement statement =
                         connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                statement.setString(1, resume.getFullName());
                statement.setString(2, uuid);
                if (statement.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            deleteSectionByResumeUuid(connection, "DELETE FROM contact WHERE resume_uuid = ?", uuid);
            insertContacts(connection, resume);

            deleteSectionByResumeUuid(connection, "DELETE FROM section WHERE resume_uuid = ?", uuid);
            insertSections(connection, resume);

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
            insertContacts(connection, resume);
            insertSections(connection, resume);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume = new Resume();
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM resume r WHERE r.uuid = ?")) {
                statement.setString(1, uuid);
                ResultSet rs = statement.executeQuery();
                if (!rs.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume.setUuid(uuid);
                resume.setFullName(rs.getString("full_name"));
            }

            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM contact c WHERE c.resume_uuid = ?")) {
                statement.setString(1, uuid);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    addContact(rs, resume);
                }
            }

            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM section s WHERE s.resume_uuid = ?")) {
                statement.setString(1, uuid);
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    addSection(rs, resume);
                }
            }
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
        return sqlHelper.transactionalExecute(connection -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM resume ORDER BY uuid, full_name")) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    resumes.putIfAbsent(uuid, resume);
                }
            }

            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    addContact(rs, resumes.get(rs.getString("resume_uuid")));
                }
            }

            try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM section")) {
                ResultSet rs = statement.executeQuery();
                while (rs.next()) {
                    addSection(rs, resumes.get(rs.getString("resume_uuid")));
                }
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

    private void addContact(ResultSet rs, Resume resume) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            resume.addContact(ContactType.valueOf(type),
                    rs.getString("value"));
        }
    }

    private void addSection(ResultSet rs, Resume resume) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            SectionType sectionType = SectionType.valueOf(type);
            resume.addSection(sectionType, JsonParser.read(rs.getString("value"), AbstractSection.class));
        }
    }

    private void insertContacts(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> e : resume.getContacts().entrySet()) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, e.getKey().name());
                statement.setString(3, e.getValue());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void insertSections(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement("INSERT INTO section (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : resume.getSections().entrySet()) {
                SectionType sectionType = e.getKey();
                AbstractSection sectionContent = e.getValue();

                statement.setString(1, resume.getUuid());
                statement.setString(2, sectionType.name());
                statement.setString(3, JsonParser.write(sectionContent, AbstractSection.class));
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void deleteSectionByResumeUuid(Connection connection, String query, String uuid) throws SQLException {
        try (PreparedStatement statement =
                     connection.prepareStatement(query)) {
            statement.setString(1, uuid);
            statement.execute();
        }
    }
}
