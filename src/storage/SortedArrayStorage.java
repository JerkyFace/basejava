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
        for (int i = size; i > index; i--) {
            storage[i] = storage[i - 1].copy();
        }
        storage[index] = resume;
    }
}
