package storage.serializer;

import model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serialization {

    boolean serialize(Resume resume, OutputStream os) throws IOException;

    Resume deserialize(InputStream is) throws IOException;
}