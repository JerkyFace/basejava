package storage.listbased;

import model.Resume;
import storage.AbstractStorage;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isPresent(Object object) {
        return (Integer) object >= 0;
    }

    @Override
    protected Integer getKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void update(Resume resume, Object index) {
        storage.set((Integer) index, resume);
    }

    @Override
    public void save(Resume resume, Object index) {
        storage.add(resume);
    }

    @Override
    public Resume get(Object index) {
        return storage.get((Integer) index);
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
