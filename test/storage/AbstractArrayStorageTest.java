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
        String uuid = "uuid2";
        Resume updatedResume = new Resume(uuid);
        storage.update(updatedResume);
        assertSame(updatedResume, storage.get(uuid));
    }

    @Test
    void updateThrowsNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("non existent resume")));
    }

    @Test
    void save() {
        String uuid = "uuid4";
        Resume resume = new Resume(uuid);
        storage.save(resume);
        assertEquals(INITIAL_AMOUNT_OF_RESUMES + 1, storage.size());
        assertSame(resume, storage.get(uuid));
    }

    @Test
    void saveThrowsExistStorageException() {
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume("uuid2")));
    }

    @Test
    void fullFillStorageAndSave() {
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
        String uuid = "uuid1";
        assertEquals(storage.get(uuid), new Resume(uuid));
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
        assertArrayEquals(storage.getAll(), resumes);
    }
}