package resumeapp.util.sql;

import org.postgresql.util.PSQLException;
import resumeapp.exception.ExistStorageException;
import resumeapp.exception.StorageException;

import java.sql.SQLException;

public class ExceptionUtil {
    public ExceptionUtil(){}

    public static StorageException convertException(SQLException e){
        if (e instanceof PSQLException && e.getSQLState().equals("23505")) {
            throw new ExistStorageException();
        }
        return new StorageException(e.getMessage());
    }
}
