package storage.arraybased;

import model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected int indexOf(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void addResume(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    protected void shiftResume(int index) {
        storage[index] = storage[size - 1];
    }
}
