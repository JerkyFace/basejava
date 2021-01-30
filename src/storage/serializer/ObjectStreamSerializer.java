package storage.serializer;

import exception.StorageException;
import model.Resume;

import java.io.*;

public class ObjectStreamSerializer implements StreamSerializer {
    @Override
    public void serialize(Resume resume, OutputStream os) throws IOException {
        try (final ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        }
    }

    @Override
    public Resume deserialize(InputStream is) throws IOException {
        try (final ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException cnf) {
            throw new StorageException("Could not read resume from file", null, cnf);
        }
    }
}
