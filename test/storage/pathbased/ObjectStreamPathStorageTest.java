package storage.pathbased;

import storage.AbstractStorageTest;
import storage.Storage;
import storage.filebased.ObjectStreamStorage;

import static org.junit.jupiter.api.Assertions.*;

class ObjectStreamPathStorageTest extends AbstractStorageTest {

    protected ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_PATH));
    }
}