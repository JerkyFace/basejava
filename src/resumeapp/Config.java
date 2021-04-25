package resumeapp;

import resumeapp.storage.Storage;
import resumeapp.storage.sqlbased.SqlStorage;


public class Config {
    private static final String PROPS = System.getenv("DATABASE_URL");
    private static final Config INSTANCE = new Config();
    private final Storage sqlStorage;

    private Config() {
        String dbUrl = "jdbc:postgresql://"
                .concat(PROPS.split("@")[1])
                .concat("?ssl=true&sslmode=verify-ca&sslfactory=org.postgresql.ssl.NonValidatingFactory");
        String dbUser = PROPS.split("/")[2].split(":")[0];
        String dbPassword = PROPS.split(":")[2].split("@")[0];
        sqlStorage = new SqlStorage(dbUrl, dbUser, dbPassword);
    }

    public static Config get() {
        return INSTANCE;
    }

    public Storage getSqlStorage() {
        return sqlStorage;
    }
}
