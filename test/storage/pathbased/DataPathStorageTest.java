package storage.pathbased;

import storage.AbstractStorageTest;
import storage.serializer.DataStreamSerializer;

class DataPathStorageTest extends AbstractStorageTest {

    protected DataPathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new DataStreamSerializer()));
    }
}