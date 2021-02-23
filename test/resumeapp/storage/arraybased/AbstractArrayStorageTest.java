package resumeapp.storage.arraybased;

import resumeapp.exception.StorageException;
import resumeapp.model.Resume;
import org.junit.jupiter.api.Test;
import resumeapp.storage.AbstractStorageTest;
import resumeapp.storage.Storage;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void storageOverflow() {
        try {
            for (int i = storage.size() + 1; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume("uuid" + i, "fullName" + i));
            }
        } catch (StorageException e) {
            fail("Exception should not be thrown.");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("odd resume", "fullName")));
    }
}