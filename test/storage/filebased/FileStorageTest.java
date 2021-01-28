package storage.filebased;

import storage.AbstractStorageTest;
import storage.serializer.ObjectStreamSerializer;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new ObjectStreamSerializer()));
    }
}