package storage.mapbased;

import storage.AbstractStorageTest;

public class MapStorageTest extends AbstractStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }
}