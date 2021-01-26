package storage.filebased;

import storage.AbstractStorageTest;
import storage.serializer.ObjectStreamSerializer;

class FileStorageTest extends AbstractStorageTest {

    protected FileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new ObjectStreamSerializer()));
    }
}