package storage.pathbased;

import storage.AbstractStorageTest;
import storage.serializer.ObjectStreamSerializer;

class PathStorageTest extends AbstractStorageTest {

    protected PathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new ObjectStreamSerializer()));
    }
}