package storage.pathbased;

import storage.AbstractStorageTest;
import storage.serializer.XmlStreamSerializer;

class XmlPathStorageTest extends AbstractStorageTest {

    protected XmlPathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new XmlStreamSerializer()));
    }
}