package resumeapp.storage;

import resumeapp.Config;
import resumeapp.exception.ExistStorageException;
import resumeapp.exception.NotExistStorageException;
import resumeapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import resumeapp.test.ResumeTestData;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIRECTORY = Config.get().getStorageDir();

    private static final int INITIAL_AMOUNT_OF_RESUMES = 3;

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static final Resume RESUME_1 = ResumeTestData.initResume(UUID_1, "fullName1");
    private static final Resume RESUME_2 = ResumeTestData.initResume(UUID_2, "fullName2");
    private static final Resume RESUME_3 = ResumeTestData.initResume(UUID_3, "fullName3");
    private static final Resume RESUME_4 = ResumeTestData.initResume(UUID_4, "fullName4");

    protected final Storage storage;

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setup() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume updatedResume = ResumeTestData.initResume(UUID_2, "Updated FullName");
        storage.update(updatedResume);
        assertEquals(updatedResume, storage.get(UUID_2));
    }

    @Test
    void updateThrowsNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () ->
                storage.update(ResumeTestData.initResume("non existent resume", "fullName")));
    }

    @Test
    void save() {
        storage.save(RESUME_4);
        assertEquals(INITIAL_AMOUNT_OF_RESUMES + 1, storage.size());
        assertEquals(RESUME_4, storage.get(UUID_4));
    }

    @Test
    void saveThrowsExistStorageException() {
        assertThrows(ExistStorageException.class, () ->
                storage.save(RESUME_2));
    }

    @Test
    void size() {
        assertEquals(INITIAL_AMOUNT_OF_RESUMES, storage.size());
    }

    @Test
    void get() {
        assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test
    void getThrowsNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> storage.get("non existent resume"));
    }

    @Test
    void delete() {
        storage.delete(UUID_1);
        assertEquals(INITIAL_AMOUNT_OF_RESUMES - 1, storage.size());
        assertThrows(NotExistStorageException.class, () -> storage.get(UUID_1));
    }

    @Test
    void deleteThrowsNotExistStorageException() {
        assertThrows(NotExistStorageException.class, () -> storage.delete("non existent resume"));
    }

    @Test
    void getAll() {
        assertEquals(3, storage.size());
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }
}