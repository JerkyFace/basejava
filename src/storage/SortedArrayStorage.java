package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int indexOf(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }

    @Override
    protected void addResume(Resume resume, int index) {
        index = Math.abs(index + 1);
        if (size - index >= 0) {
            System.arraycopy(storage, index, storage, index + 1, size - index);
        }
        storage[index] = resume;
    }

    @Override
    protected void shiftResume(int index) {
        if (size - 1 - index >= 0) {
            System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
        }
    }
}
