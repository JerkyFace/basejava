package storage.listbased;

import model.Resume;
import storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isPresent(Object index) {
        return (int) index >= 0;
    }

    @Override
    protected Integer getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
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
    public List<Resume> getAll() {
        return new ArrayList<>(storage);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
