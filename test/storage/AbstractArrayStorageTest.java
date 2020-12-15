package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {
    private static final int INITIAL_AMOUNT_OF_RESUMES = 3;

    private final Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        for (int i = 1; i <= INITIAL_AMOUNT_OF_RESUMES; i++) {
            storage.save(new Resume("uuid".concat(String.valueOf(i))));
        }
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume updatedResume = new Resume("uuid2");
        storage.update(updatedResume);
        assertSame(updatedResume, storage.get("uuid2"));
    }

    @Test
    void updateThrowsNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("non existent resume")));
    }

    @Test
    void save() {
        Resume resume = new Resume("uuid4");
        storage.save(resume);
        assertEquals(INITIAL_AMOUNT_OF_RESUMES + 1, storage.size());
        assertSame(storage.get("uuid4"), resume);
    }

    @Test
    void saveThrowsExistStorageException() {
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume("uuid2")));
    }

    @Test
    void saveIntoFullStorage() {
        int storageLimit = 100;
        try {
            for (int i = storage.size() + 1; i <= storageLimit; i++) {
                storage.save(new Resume("uuid".concat(String.valueOf(i))));
            }
        } catch (StorageException e) {
            fail("Exception should not be thrown.");
        }

        Exception exception = assertThrows(StorageException.class, () -> storage.save(new Resume("odd resume")));

        String expectedExceptionMessage = "Unable to add resume with uuid 'odd resume'. The storage is full.";
        assertTrue(exception.getMessage().contains(expectedExceptionMessage));
    }

    @Test
    void size() {
        assertEquals(INITIAL_AMOUNT_OF_RESUMES, storage.size());
    }

    @Test
    void get() {
        for (int i = 1; i <= storage.size(); i++) {
            assertEquals(storage.get("uuid".concat(String.valueOf(i))),
                    new Resume("uuid".concat(String.valueOf(i))));
        }
    }

    @Test
    void getThrowsNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> storage.get("non existent resume"));
    }

    @Test
    void delete() {
        String uuid = "uuid1";
        storage.delete(uuid);
        assertEquals(INITIAL_AMOUNT_OF_RESUMES - 1, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get(uuid));
    }

    @Test
    void deleteThrowsNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> storage.get("non existent resume"));
    }

    @Test
    void getAll() {
        Resume[] resumes = storage.getAll();
        assertEquals(3, resumes.length);
        for (Resume resume : resumes) {
            assertSame(storage.get(resume.getUuid()), resume);
        }
    }
}