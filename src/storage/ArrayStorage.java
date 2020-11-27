package storage;

import model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private final Resume[] storage = new Resume[20];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size() - 1, null);
        size = 0;
    }

    public void update(Resume resume) {
        int updateResumeIndex = indexOf(resume);
        if (updateResumeIndex == -1) {
            System.out.println("Resume not found.");
        } else {
            storage[updateResumeIndex] = resume;
            System.out.println("Resume with uuid " + resume.getUuid() + " successfully updated.");
        }
    }

    /**
     * Pushes resume object to the storage array
     *
     * @param resume
     */
    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Cannot save empty object");
        } else if (size == storage.length) {
            System.out.println("The storage is full");
        } else if (get(resume.getUuid(), false) == null) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("resume with uuid " + resume.getUuid() + " already exists");
        }
    }

    /**
     * @param uuid
     * @return found Resume object or null
     */
    public Resume get(String uuid, boolean isLogged) {
        Resume findResume = new Resume(uuid);
        int findResumeIndex = indexOf(findResume);
        if (findResumeIndex == -1) {
            System.out.print(isLogged ? "\nResume not found.\n" : "");
            return null;
        } else {
            return storage[findResumeIndex];
        }
    }

    /**
     * Nullifies found storage element, shifts array one position left starting form
     * deleted element
     *
     * @param uuid
     */
    public void delete(String uuid) {
        boolean isPresent = false;
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                isPresent = true;
                if (size - 1 - i >= 0) {
                    System.arraycopy(storage, i + 1, storage, i, size - 1 - i);
                }
                size--;
                // prevents doubling of last element when array is full
                storage[size] = null;
                break;
            }
        }
        System.out.print(isPresent ? "" : "\nThere is no such resume\n");
    }

    /**
     * @return array, contains only resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    /**
     * @return the size of non-null part of the storage array
     */
    public int size() {
        return size;
    }

    private int indexOf(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
