package storage.arraybased;

import exception.StorageException;
import model.Resume;
import org.junit.jupiter.api.Test;
import storage.AbstractStorageTest;
import storage.Storage;

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
                storage.save(new Resume("uuid" + i));
            }
        } catch (StorageException e) {
            fail("Exception should not be thrown.");
        }
        assertThrows(StorageException.class, () -> storage.save(new Resume("odd resume")));
    }
}