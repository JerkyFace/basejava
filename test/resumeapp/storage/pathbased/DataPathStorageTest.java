package resumeapp.storage.pathbased;

import resumeapp.storage.AbstractStorageTest;
import resumeapp.storage.serializer.DataStreamSerializer;

class DataPathStorageTest extends AbstractStorageTest {

    protected DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getName(), new DataStreamSerializer()));
    }
}