package storage;

import model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void update(Resume resume) {
        int updateResumeIndex = indexOf(resume);
        if (updateResumeIndex < 0) {
            System.out.println("Resume not found.");
        } else {
            storage[updateResumeIndex] = resume;
            System.out.println("Resume with uuid " + resume.getUuid() + " successfully updated.");
        }
    }

    public int size() {
        return size;
    }


    public Resume get(String uuid) {
        Resume findResume = new Resume(uuid);
        int findResumeIndex = indexOf(findResume);
        if (findResumeIndex < 0) {
            System.out.print("\nResume not found.\n");
            return null;
        }
        return storage[findResumeIndex];
    }

    public void delete(String uuid) {
        Resume findResume = new Resume(uuid);
        int findResumeIndex = indexOf(findResume);
        if (findResumeIndex < 0) {
            System.out.println("\nThere is no such resume\n");
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

    protected abstract int indexOf(Resume findResume);
}
