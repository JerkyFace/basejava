package resumeapp.test;

import resumeapp.model.Resume;
import resumeapp.storage.Storage;
import resumeapp.storage.listbased.ListStorage;


/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final Storage ARRAY_STORAGE = new ListStorage();

    public static void main(String[] args) {
        ARRAY_STORAGE.save(new Resume("1", "1"));
        ARRAY_STORAGE.save(new Resume("2", "2"));
        ARRAY_STORAGE.save(new Resume("3", "3"));

        System.out.println(ARRAY_STORAGE.getAllSorted().indexOf(new Resume("2", "")));
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(r);
        }
    }
}
