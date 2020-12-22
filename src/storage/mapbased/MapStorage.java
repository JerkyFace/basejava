package storage.mapbased;

import exception.StorageException;
import model.Resume;
import storage.AbstractStorage;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected int indexOf(String uuid) {
        return 0;
    }

    @Override
    protected void update(Resume resume, int index) {

    }

    @Override
    protected void save(Resume resume, int index) {
        if (resume == null) {
            throw new StorageException("Cannot save empty resume", null);
        }
    }

    @Override
    protected Resume get(int index) {
        return null;
    }

    @Override
    protected void delete(int index) {

    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
