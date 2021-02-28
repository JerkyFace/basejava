package resumeapp.storage.dbbased;

import resumeapp.storage.AbstractStorageTest;

public class SqlStorageTest extends AbstractStorageTest {
    protected SqlStorageTest() {
        super(new DataBaseStorage(DB_URL, DB_USER, DB_PASSWORD));
    }
}
