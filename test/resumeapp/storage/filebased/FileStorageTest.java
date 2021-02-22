package resumeapp.storage.filebased;

import resumeapp.storage.AbstractStorageTest;
import resumeapp.storage.serializer.ObjectStreamSerializer;

public class FileStorageTest extends AbstractStorageTest {

    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIRECTORY, new ObjectStreamSerializer()));
    }
}