package resumeapp.storage.serializer;

import resumeapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface StreamSerializer {

    void serialize(Resume resume, OutputStream os) throws IOException;

    Resume deserialize(InputStream is) throws IOException;
}
