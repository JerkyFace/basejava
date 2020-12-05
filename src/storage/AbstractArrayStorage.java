package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 100;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int updateResumeIndex = indexOf(resume.getUuid());
        if (updateResumeIndex < 0) {
            System.out.print("\nResume with uuid '" + resume.getUuid() + "' not found.\n");
        } else {
            storage[updateResumeIndex] = resume;
            System.out.println("Resume with uuid '" + resume.getUuid() + "' successfully updated.");
        }
    }

    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Cannot save empty object");
        } else {
            int findResumeIndex = indexOf(resume.getUuid());
            if (size >= STORAGE_LIMIT) {
                System.out.println("The storage is full");
            } else if (findResumeIndex >= 0) {
                System.out.println("resume with uuid '" + resume.getUuid() + "' already exists");
            } else {
                addResume(resume, findResumeIndex);
                size++;
            }
        }
    }

    public int size() {
        return size;
    }


    public Resume get(String uuid) {
        int findResumeIndex = indexOf(uuid);
        if (findResumeIndex < 0) {
            System.out.print("\nResume with uuid '" + uuid + "' not found.\n");
            return null;
        }
        return storage[findResumeIndex];
    }

    public void delete(String uuid) {
        int findResumeIndex = indexOf(uuid);
        if (findResumeIndex < 0) {
            System.out.print("\nResume with uuid '" + uuid + "' not found.\n");
        } else if (size - 1 - findResumeIndex >= 0) {
            System.arraycopy(storage, findResumeIndex + 1,
                    storage, findResumeIndex, size - 1 - findResumeIndex);
            size--;
            storage[size] = null;
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected abstract int indexOf(String uuid);

    protected abstract void addResume(Resume resume, int index);
}
