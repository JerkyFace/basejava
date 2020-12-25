package storage.listbased;

import exception.StorageException;
import model.Resume;
import storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isPresent(Object object) {
        return (int) object >= 0;
    }

    @Override
    protected Object indexOf(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume, Object index) {
        storage.set((int) index, resume);
    }

    @Override
    public void save(Resume resume, Object index) {
        if (resume == null) {
            throw new StorageException("Cannot save empty resume", null);
        }
        storage.add(resume);
    }

    @Override
    public Resume get(Object index) {
        return storage.get((int) index);
    }

    @Override
    protected void delete(Object index) {
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
