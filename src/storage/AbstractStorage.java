package storage;

import model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume resume) {
        update(resume, indexOf(resume.getUuid()));
    }

    @Override
    public void save(Resume resume) {
        save(resume, indexOf(resume.getUuid()));
    }

    @Override
    public Resume get(String uuid) {
        return get(indexOf(uuid));
    }

    @Override
    public void delete(String uuid) {
        delete(indexOf(uuid));
    }

    protected abstract int indexOf(String uuid);

    protected abstract void update(Resume resume, int index);

    protected abstract void save(Resume resume, int index);

    protected abstract Resume get(int index);

    protected abstract void delete(int index);
}
