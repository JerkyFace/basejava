package resumeapp.storage.arraybased;

import resumeapp.exception.StorageException;
import resumeapp.model.Resume;
import resumeapp.storage.AbstractStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    public static final int STORAGE_LIMIT = 100;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    @Override
    protected boolean isPresent(Integer resume) {
        return resume >= 0;
    }

    protected abstract void addResume(Resume resume, int index);

    protected abstract void shiftResume(int index);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void doUpdate(Resume resume, Integer index) {
        storage[index] = resume;
        System.out.println("Resume with uuid '" + resume.getUuid() + "' successfully updated.");
    }

    public void doSave(Resume resume, Integer index) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Unable to add resume with uuid '"
                    .concat(resume.getUuid())
                    .concat("'. The resumeapp.storage is full."), resume.getUuid());
        }
        addResume(resume, index);
        size++;
    }

    public int size() {
        return size;
    }

    public Resume doGet(Integer index) {
        return storage[index];
    }

    public void doDelete(Integer index) {
        shiftResume(index);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(Arrays.asList(Arrays.copyOfRange(storage, 0, size)));
    }
}
