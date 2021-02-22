package resumeapp.storage.pathbased;

import resumeapp.storage.AbstractStorageTest;
import resumeapp.storage.serializer.ObjectStreamSerializer;

public class PathStorageTest extends AbstractStorageTest {

    protected PathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getName(), new ObjectStreamSerializer()));
    }
}