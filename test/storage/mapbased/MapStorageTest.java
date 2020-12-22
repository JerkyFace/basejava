package storage.mapbased;

import storage.arraybased.AbstractArrayStorageTest;

class MapStorageTest extends AbstractArrayStorageTest {

    public MapStorageTest() {
        super(new MapStorage());
    }
}