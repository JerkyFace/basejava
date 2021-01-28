package storage.pathbased;

import storage.AbstractStorageTest;
import storage.serializer.ObjectStreamSerializer;

public class PathStorageTest extends AbstractStorageTest {

    protected PathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.toString(), new ObjectStreamSerializer()));
    }
}