/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        int size = this.size();
        for (int i = 0; i < size; i++)
            storage[i] = null;
    }

    /**
     * Pushes resume object to the non-null part of storage array
     * 
     * @param resume
     */
    void save(Resume resume) {
        if (resume == null)
            System.out.println("Cannot save empty object");
        else if (get(resume.uuid) == null)
            storage[this.size()] = resume;
        else
            System.out.println("resume " + resume.uuid + " already exist");
    }

    /**
     * 
     * @param uuid
     * @return found Resume object or null
     */
    Resume get(String uuid) {
        int size = this.size();
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid)
                return storage[i];
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
        int size = this.size();
        for (int i = 0; i < this.size(); i++) {
            if (storage[i].uuid == uuid) {
                storage[i] = null;
                for (int j = i; j < size; j++) {
                    storage[j] = storage[j + 1];
                }
                break;
            }
        }
    }

    /**
     * @return array, contains only resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] tempArray = new Resume[this.size()];
        for (int i = 0; i < this.size(); i++)
            tempArray[i] = storage[i];

        return tempArray;
    }

    /**
     * Counts object within storage array until meets null
     * 
     * @return the size of non-null part of the storage array
     */
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
