package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.ResumeTestData;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIRECTORY = new File("./filestorage");
    private static final int INITIAL_AMOUNT_OF_RESUMES = 3;

    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";
    private static final String UUID4 = "uuid4";

    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(ResumeTestData.initResume(UUID1, "fullName1"));
        storage.save(ResumeTestData.initResume(UUID2, "fullName2"));
        storage.save(ResumeTestData.initResume(UUID3, "fullName3"));
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume updatedResume = ResumeTestData.initResume(UUID2, "Updated FullName");
        storage.update(updatedResume);
        assertEquals(updatedResume, storage.get(UUID2));
    }

    @Test
    void updateThrowsNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () ->
                storage.update(ResumeTestData.initResume("non existent resume", "fullName")));
    }

    @Test
    void save() {
        Resume resume = ResumeTestData.initResume(UUID4, "fullName3");
        storage.save(resume);
        assertEquals(INITIAL_AMOUNT_OF_RESUMES + 1, storage.size());
        assertEquals(resume, storage.get(UUID4));
    }

    @Test
    void saveThrowsExistStorageException() {
        assertThrows(ExistStorageException.class, () ->
                storage.save(ResumeTestData.initResume(UUID2, "fullName2")));
    }

    @Test
    void size() {
        assertEquals(INITIAL_AMOUNT_OF_RESUMES, storage.size());
    }

    @Test
    void get() {
        assertEquals(ResumeTestData.initResume(UUID1, "fullName1"), storage.get(UUID1));
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
        assertEquals(3, storage.size());
        List<Resume> expected = List.of(ResumeTestData.initResume(UUID1, "fullName1"),
                ResumeTestData.initResume(UUID2, "fullName2"),
                ResumeTestData.initResume(UUID3, "fullName3"));
        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }
}