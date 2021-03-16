package resumeapp.storage.sqlbased;

import resumeapp.Config;
import resumeapp.storage.AbstractStorageTest;

public class SqlStorageTest extends AbstractStorageTest {
    protected SqlStorageTest() {
        super(Config.get().getSqlStorage());
    }
}
