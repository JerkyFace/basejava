package resumeapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPS = new File("config/resume_app.properties");
    private static final Config INSTANCE = new Config();
    private final File storageDir;

    private Config() {
        try (InputStream is = new FileInputStream(PROPS)) {
            Properties properties = new Properties();
            properties.load(is);
            storageDir = new File(properties.getProperty("resumeapp.storage.dir"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid properties file " + PROPS.getAbsolutePath(), e);
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public File getStorageDir() {
        return storageDir;
    }
}
