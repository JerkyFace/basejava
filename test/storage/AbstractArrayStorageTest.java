package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {
    private static final int INITIAL_AMOUNT_OF_RESUMES = 2;

    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";

    private final Storage storage;

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        for (int i = 1; i <= INITIAL_AMOUNT_OF_RESUMES; i++) {
            storage.save(new Resume("uuid" + i));
        }
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume updatedResume = new Resume(UUID2);
        storage.update(updatedResume);
        assertSame(updatedResume, storage.get(UUID2));
    }

    @Test
    void updateThrowsNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("non existent resume")));
    }

    @Test
    void save() {
        Resume resume = new Resume(UUID3);
        storage.save(resume);
        assertEquals(INITIAL_AMOUNT_OF_RESUMES + 1, storage.size());
        assertSame(resume, storage.get(UUID3));
    }

    @Test
    void saveThrowsExistStorageException() {
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID2)));
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

    @Test
    void size() {
        assertEquals(INITIAL_AMOUNT_OF_RESUMES, storage.size());
    }

    @Test
    void get() {
        assertEquals(storage.get(UUID1), new Resume(UUID1));
    }

    @Test
    void getThrowsNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> storage.get("non existent resume"));
    }

    @Test
    void delete() {
        storage.delete(UUID1);
        assertEquals(INITIAL_AMOUNT_OF_RESUMES - 1, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID1));
    }

    @Test
    void deleteThrowsNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> storage.get("non existent resume"));
    }

    @Test
    void getAll() {
        assertEquals(2, storage.getAll().length);
        Resume[] resumes = {new Resume(UUID1), new Resume(UUID2)};
        assertArrayEquals(storage.getAll(), resumes);
    }
}