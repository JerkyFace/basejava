package resumeapp.storage.pathbased;

import resumeapp.storage.AbstractStorageTest;
import resumeapp.storage.serializer.JsonStreamSerializer;

class JsonPathStorageTest extends AbstractStorageTest {

    protected JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getName(), new JsonStreamSerializer()));
    }
}