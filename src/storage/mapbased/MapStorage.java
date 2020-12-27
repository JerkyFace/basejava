package storage.mapbased;

import model.Resume;
import storage.AbstractStorage;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isPresent(Object object) {
        return storage.get(object) != null;
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    protected void update(Resume resume, Object index) {
        save(resume, index);
    }

    @Override
    protected void save(Resume resume, Object index) {
        storage.put((String) index, resume);
    }

    @Override
    protected Resume get(Object index) {
        return storage.get(index);
    }

    @Override
    protected void delete(Object index) {
        storage.remove(index);
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
