package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int index = indexOf(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        } else {
            storage[index] = resume;
            System.out.println("Resume with uuid '" + resume.getUuid() + "' successfully updated.");
        }
    }

    public void save(Resume resume) {
        if (resume == null) {
            throw new StorageException("Cannot save empty resume", null);
        } else {
            int index = indexOf(resume.getUuid());
            if (size >= STORAGE_LIMIT) {
                throw new StorageException("Unable to add resume with uuid '"
                        .concat(resume.getUuid())
                        .concat("'. The storage is full."), resume.getUuid());
            } else if (index >= 0) {
                throw new ExistStorageException(resume.getUuid());
            } else {
                addResume(resume, index);
                size++;
            }
        }
    }

    public int size() {
        return size;
    }


    public Resume get(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    public void delete(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            throw new ExistStorageException(uuid);
        } else {
            shiftResume(index);
            storage[size - 1] = null;
            size--;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected abstract int indexOf(String uuid);

    protected abstract void addResume(Resume resume, int index);

    protected abstract void shiftResume(int index);
}
