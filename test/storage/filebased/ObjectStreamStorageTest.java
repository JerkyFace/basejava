package storage.filebased;

import storage.AbstractStorageTest;

class ObjectStreamStorageTest extends AbstractStorageTest {

    protected ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIRECTORY));
    }
}