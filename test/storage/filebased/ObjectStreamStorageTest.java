package storage.filebased;

import storage.AbstractStorageTest;
import storage.Storage;

import static org.junit.jupiter.api.Assertions.*;

class ObjectStreamStorageTest extends AbstractStorageTest {

    protected ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIRECTORY));
    }
}