package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {

    private static final int INITIAL_AMOUNT_OF_RESUMES = 2;

    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";

    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        for (int i = 1; i <= INITIAL_AMOUNT_OF_RESUMES; i++) {
            storage.save(new Resume("uuid" + i, "fullName" + i));
        }
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume updatedResume = new Resume(UUID2, "Updated FullName");
        storage.update(updatedResume);
        assertSame(updatedResume, storage.get(UUID2));
    }

    @Test
    void updateThrowsNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("non existent resume", "fullName")));
    }

    @Test
    void save() {
        Resume resume = new Resume(UUID3, "fullName3");
        storage.save(resume);
        assertEquals(INITIAL_AMOUNT_OF_RESUMES + 1, storage.size());
        assertSame(resume, storage.get(UUID3));
    }

    @Test
    void saveThrowsExistStorageException() {
        assertThrows(ExistStorageException.class, () -> storage.save(new Resume(UUID2, "fullName2")));
    }

    @Test
    void size() {
        assertEquals(INITIAL_AMOUNT_OF_RESUMES, storage.size());
    }

    @Test
    void get() {
        assertEquals(new Resume(UUID1, "fullName1"), storage.get(UUID1));
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
        assertEquals(2, storage.size());
        List<Resume> expected = List.of(new Resume(UUID1, "fullName1"), new Resume(UUID2, "fullName2"));
        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }
}