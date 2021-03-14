package resumeapp.exception;

public class StorageException extends RuntimeException {
    private String uuid = null;

    public StorageException(String message) {
        this(message, null, null);
    }

    public StorageException(String message, String uuid) {
        super(message);
        this.uuid = uuid;
    }

    public StorageException(String message, String uuid, Exception e) {
        super(message, e);
        this.uuid = uuid;
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }

    public String getUuid() {
        return uuid;
    }
}
