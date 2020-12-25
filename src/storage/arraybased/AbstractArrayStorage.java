package storage.arraybased;

import exception.StorageException;
import model.Resume;
import storage.AbstractStorage;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    public static final int STORAGE_LIMIT = 100;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume, Object index) {
        storage[castObjectToInt(index)] = resume;
        System.out.println("Resume with uuid '" + resume.getUuid() + "' successfully updated.");
    }

    public void save(Resume resume, Object index) {
        if (resume == null) {
            throw new StorageException("Cannot save empty resume", null);
        } else if (size >= STORAGE_LIMIT) {
            throw new StorageException("Unable to add resume with uuid '"
                    .concat(resume.getUuid())
                    .concat("'. The storage is full."), resume.getUuid());
        }
        addResume(resume, castObjectToInt(index));
        size++;
    }

    public int size() {
        return size;
    }

    public Resume get(Object index) {
        return storage[castObjectToInt(index)];
    }

    public void delete(Object index) {
        shiftResume(castObjectToInt(index));
        storage[size - 1] = null;
        size--;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    protected boolean isPresent(Object object) {
        return (int) object >= 0;
    }

    protected abstract void addResume(Resume resume, int index);

    protected abstract void shiftResume(int index);
}
