package storage.mapbased;

import model.Resume;
import storage.AbstractStorage;

import java.util.*;

public class MapStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isPresent(Object resume) {
        return storage.containsKey(resume);
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    protected void update(Resume resume, Object key) {
        save(resume, key);
    }

    @Override
    protected void save(Resume resume, Object key) {
        storage.put((String) key, resume);
    }

    @Override
    protected Resume get(Object key) {
        return storage.get(key);
    }

    @Override
    protected void delete(Object key) {
        storage.remove(key);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
