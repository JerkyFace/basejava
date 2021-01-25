package storage.pathbased;

import storage.AbstractStorageTest;
import storage.serializer.ObjectStreamSerialization;
import storage.serializer.XmlStreamSerialization;

class XmlPathStorageTest extends AbstractStorageTest {

    protected XmlPathStorageTest() {
        super(new PathStorage(STORAGE_PATH, new XmlStreamSerialization()));
    }
}