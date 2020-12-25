package storage;

import exception.ExistStorageException;
import exception.NotExistStorageException;
import exception.StorageException;
import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        update(resume, getIfPresent(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        Object index = indexOf(resume.getUuid());
        if (isPresent(index)) {
            throw new ExistStorageException(resume.getUuid());
        }
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

    private Object getIfPresent(String uuid) {
        Object index = indexOf(uuid);
        if (!isPresent(index)) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }


    protected int castObjectToInt(Object object) {
        if (object == null) {
            throw new StorageException("Could not get index of resume.", null);
        }
        int result = -1;
        try {
            result = (int) object;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected abstract boolean isPresent(Object object);

    protected abstract Object indexOf(String uuid);

    protected abstract void update(Resume resume, Object index);

    protected abstract void save(Resume resume, Object index);

    protected abstract Resume get(Object index);

    protected abstract void delete(Object index);
}
