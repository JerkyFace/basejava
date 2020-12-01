package test;

import model.Resume;
import storage.SortedArrayStorage;


/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        for (int i = 1; i <= 9; i++) {
            ARRAY_STORAGE.save(new Resume("uuid" + i));
        }

        printAll();
        System.out.println(ARRAY_STORAGE.get("uuid2"));
        System.out.println(ARRAY_STORAGE.get("uuid11"));
        ARRAY_STORAGE.delete("uuid2");
        printAll();
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
