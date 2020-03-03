/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        for (int i = 0; i < this.size(); i++)
            storage[i] = null;
    }

    void save(Resume resume) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] == null) {
                storage[i] = resume;
                break;
            }
        }
    }

    Resume get(String uuid) {
        for (int i = 0; i < this.size(); i++) {
            if (storage[i].uuid == uuid)
                return storage[i];
        }
        return null;
    }

    void delete(String uuid) {

    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] tempArray = new Resume[this.size()];
        for (int i = 0; i < this.size(); i++)
            tempArray[i] = storage[i];

        return tempArray;
    }

    int size() {
        int size = 0;
        for (int i = 0; i < storage.length; i++) {
            size = i;
            if (storage[i] == null)
                return size;
        }
        return size;
    }
}
