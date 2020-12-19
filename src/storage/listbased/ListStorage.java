package storage.listbased;

import exception.ExistStorageException;
import exception.StorageException;
import model.Resume;
import storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected int indexOf(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume, int index) {
        storage.set(index, resume);
    }

    @Override
    public void save(Resume resume, int index) {
        if (resume == null) {
            throw new StorageException("Cannot save empty resume", null);
        } else if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        storage.add(resume);
    }

    @Override
    public Resume get(int index) {
        return storage.get(index);
    }

    @Override
    protected void delete(int index) {
        storage.remove(get(index));
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
