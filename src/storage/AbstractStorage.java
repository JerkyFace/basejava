package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        if (resume == null) {
            throw new StorageException("Cannot save empty resume", null);
        }
        update(resume, getIfPresent(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        if (resume == null) {
            throw new StorageException("Cannot save empty resume", null);
        }
        Object key = getKey(resume.getUuid());
        if (isPresent(key)) {
            throw new ExistStorageException(resume.getUuid());
        }
        save(resume, getKey(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return get(getIfPresent(uuid));
    }

    @Override
    public void delete(String uuid) {
        delete(getIfPresent(uuid));
    }

    private Object getIfPresent(String uuid) {
        Object key = getKey(uuid);
        if (!isPresent(key)) {
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract boolean isPresent(Object resume);

    protected abstract Object getKey(String uuid);

    protected abstract void update(Resume resume, Object key);

    protected abstract void save(Resume resume, Object key);

    protected abstract Resume get(Object key);

    protected abstract void delete(Object key);
}
