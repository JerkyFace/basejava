/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    private int size = 0;

    void clear() {
        for (int i = 0; i < size; i++) {
            storage[i] = null;
        }
        size = 0;
    }

    /**
     * Pushes resume object to the non-null part of storage array
     * 
     * @param resume
     */
    void save(Resume resume) {
        if (resume == null) {
            System.out.println("Cannot save empty object");
        } else if (size == storage.length) {
            System.out.println("The storage is full");
        } else if (get(resume.uuid) == null) {
            storage[size] = resume;
            size++;
        } else {
            System.out.println("resume " + resume.uuid + " already exist");
        }
    }

    /**
     * 
     * @param uuid
     * @return found Resume object or null
     */
    Resume get(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                return storage[i];
            }
        }
        return null;
    }

    /**
     * Nullifies found storage element, shifts array one position left starting form
     * deleted element
     * 
     * @param uuid
     */
    void delete(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid) {
                for (int j = i; j < size - 1; j++) {
                    storage[j] = storage[j + 1];
                }
                size--;
                // prevents doubling of last element when array is full
                storage[size] = null;
                break;
            }
        }
    }

    /**
     * @return array, contains only resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[size];
        for (int i = 0; i < size; i++) {
            resumes[i] = storage[i];
        }
        return resumes;
    }

    /**
     * @return the size of non-null part of the storage array
     */
    int size() {
        return size;
    }
}
