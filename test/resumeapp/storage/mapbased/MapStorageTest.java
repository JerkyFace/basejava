package resumeapp.storage.mapbased;

import resumeapp.storage.AbstractStorageTest;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }
}