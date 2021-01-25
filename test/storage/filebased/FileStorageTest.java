package storage.filebased;

import storage.AbstractStorageTest;
import storage.serializer.ObjectStreamSerialization;

class FileStorageTest extends AbstractStorageTest {

    protected FileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new ObjectStreamSerialization()));
    }
}