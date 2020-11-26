package test;

import model.Resume;
import storage.ArrayStorage;


/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {

        for (int i = 1; i <= 20; i++) {
            ARRAY_STORAGE.save(new Resume("uuid" + i));
        }

        printAll();
        System.out.println(ARRAY_STORAGE.get("uuid1", true));
        ARRAY_STORAGE.get("000", true);
        ARRAY_STORAGE.delete("5555");
        ARRAY_STORAGE.update(new Resume("6666"));
        ARRAY_STORAGE.update(new Resume("uuid5"));
        printAll();
        ARRAY_STORAGE.delete("uuid20");
        System.out.println(ARRAY_STORAGE.size());
        printAll();
        ARRAY_STORAGE.delete("uuid1");
        System.out.println(ARRAY_STORAGE.size());
        printAll();
        ARRAY_STORAGE.save(new Resume("uuid1"));
        ARRAY_STORAGE.save(new Resume("uuid20"));
        printAll();
        System.out.println(ARRAY_STORAGE.size());
        ARRAY_STORAGE.save(new Resume("uuid21"));
    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
        System.out.println();
    }
}
