package storage;

import exception.NotExistStorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        update(resume, getIfPresent(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        save(resume, indexOf(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return get(getIfPresent(uuid));
    }

    @Override
    public void delete(String uuid) {
        delete(getIfPresent(uuid));
    }

    private int getIfPresent(String uuid) {
        int index = indexOf(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    protected abstract int indexOf(String uuid);

    protected abstract void update(Resume resume, int index);

    protected abstract void save(Resume resume, int index);

    protected abstract Resume get(int index);

    protected abstract void delete(int index);
}
