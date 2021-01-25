package storage.pathbased;

import storage.AbstractStorageTest;
import storage.serializer.ObjectStreamSerialization;

class PathStorageTest extends AbstractStorageTest {

    protected PathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new ObjectStreamSerialization()));
    }
}