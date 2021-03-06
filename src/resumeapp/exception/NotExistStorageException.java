package resumeapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Resume with uuid '".concat(uuid).concat("' not exists."), uuid);
    }
}
