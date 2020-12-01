package storage;

import model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        if (resume == null) {
            System.out.println("Cannot save empty object");
        } else {
            int findResumeIndex = indexOf(resume);
            if (size >= STORAGE_LIMIT) {
                System.out.println("The storage is full");
            } else if (findResumeIndex == -1) {
                storage[size] = resume;
                size++;
            } else {
                System.out.println("resume with uuid " + resume.getUuid() + " already exists");
            }
        }
    }

    @Override
    protected int indexOf(Resume resume) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                return i;
            }
        }
        return -1;
    }
}
