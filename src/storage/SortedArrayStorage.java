package storage;

import model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Cannot save empty object");
        } else {
            int findResumeIndex = indexOf(resume);
            if (size >= STORAGE_LIMIT) {
                System.out.println("The storage is full");
            } else if (findResumeIndex >= 0) {
                System.out.println("resume with uuid " + resume.getUuid() + " already exists");
            } else {
                findResumeIndex = Math.abs(findResumeIndex + 1);
                for (int i = size; i > findResumeIndex; i--) {
                    storage[i] = storage[i - 1].copy();
                }
                storage[findResumeIndex] = resume;
                size++;
            }
        }
    }

    @Override
    protected int indexOf(Resume findResume) {
        return Arrays.binarySearch(storage, 0, size, findResume);
    }
}
