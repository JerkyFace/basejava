/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[100];

    void clear() {
        int size = this.size();
        for (int i = 0; i < size; i++)
            storage[i] = null;
    }

    // todo: get rid of for loop
    void save(Resume resume) {
        if (resume == null) {
            System.out.println("Cannot save empty object");
        } else if (get(resume.uuid) == null) {
            for (int i = this.size(); i < storage.length; i++) {
                if (storage[i] == null) {
                    storage[i] = resume;
                    break;
                }
            }
        } else {
            System.out.println("resume " + resume.uuid + " already exist");
        }
    }

    Resume get(String uuid) {
        int size = this.size();
        for (int i = 0; i < size; i++) {
            if (storage[i].uuid == uuid)
                return storage[i];
        }
        return null;
    }

    // todo: debug. Method doubles last element
    void delete(String uuid) {
        for (int i = 0; i < this.size(); i++) {
            if (storage[i].uuid == uuid) {
                storage[i] = null;
                for (int j = i; j < this.size() - i; j++) {
                    storage[j] = storage[j + 1];
                }
                break;
            }
        }
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
