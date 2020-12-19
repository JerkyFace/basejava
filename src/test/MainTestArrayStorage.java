package test;

import model.Resume;
import storage.Storage;
import storage.arraybased.SortedArrayStorage;
import storage.listbased.ListStorage;


/**
 * Test for your ArrayStorage implementation
 */
public class MainTestArrayStorage {
    static final SortedArrayStorage ARRAY_STORAGE = new SortedArrayStorage();


    public static void main(String[] args) {

    }

    static void printAll() {
        System.out.println("\nGet All");
        for (Resume r : ARRAY_STORAGE.getAll()) {
            System.out.println(r);
        }
    }
}
