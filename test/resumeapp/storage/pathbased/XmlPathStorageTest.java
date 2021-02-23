package resumeapp.storage.pathbased;

import resumeapp.storage.AbstractStorageTest;
import resumeapp.storage.serializer.XmlStreamSerializer;

class XmlPathStorageTest extends AbstractStorageTest {

    protected XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIRECTORY.getName(), new XmlStreamSerializer()));
    }
}