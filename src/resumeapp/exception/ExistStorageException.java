package resumeapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Resume with uuid '".concat(uuid).concat("' already exists."), uuid);
    }

    public ExistStorageException() {
        super("Duplicate key value violates unique constraint");
    }
}
