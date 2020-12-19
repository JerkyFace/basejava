package test;

import model.Resume;
import storage.arraybased.SortedArrayStorage;


/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();

    public static void main(String[] args) {
        for (int i = 100; i >= 1; i--) {
            ARRAY_STORAGE.save(new Resume("uuid" + i));
        }

        printAll();
        System.out.println("---");
        System.out.println(ARRAY_STORAGE.get("uuid2"));
        printAll();
        System.out.println(ARRAY_STORAGE.size());
        ARRAY_STORAGE.delete("uuid30");
        printAll();
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
