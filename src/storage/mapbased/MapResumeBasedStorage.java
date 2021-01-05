package storage.mapbased;

import model.Resume;
import storage.AbstractStorage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeBasedStorage extends AbstractStorage {

    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected boolean isPresent(Object resume) {
        return resume != null;
    }

    @Override
    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void update(Resume resume, Object key) {
        save(resume, resume.getUuid());
    }

    @Override
    protected void save(Resume resume, Object key) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected Resume get(Object key) {
        return (Resume) key;
    }

    @Override
    protected void delete(Object key) {
        storage.remove(((Resume) key).getUuid());
    }

    @Override
    protected List<Resume> getAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public int size() {
        return storage.size();
    }
}
