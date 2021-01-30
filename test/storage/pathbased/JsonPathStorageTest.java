package storage.pathbased;

import storage.AbstractStorageTest;
import storage.serializer.JsonStreamSerializer;

class JsonPathStorageTest extends AbstractStorageTest {

    protected JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getName(), new JsonStreamSerializer()));
    }
}