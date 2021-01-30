package storage.pathbased;

import storage.AbstractStorageTest;
import storage.serializer.JsonStreamSerializer;
import storage.serializer.XmlStreamSerializer;

class JsonPathStorageTest extends AbstractStorageTest {

    protected JsonPathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new JsonStreamSerializer()));
    }
}