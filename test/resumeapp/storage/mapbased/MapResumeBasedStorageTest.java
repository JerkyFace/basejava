package resumeapp.storage.mapbased;

import resumeapp.storage.AbstractStorageTest;

public class MapResumeBasedStorageTest extends AbstractStorageTest {

    public MapResumeBasedStorageTest() {
        super(new MapResumeBasedStorage());
    }
}