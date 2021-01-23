package storage;

import exception.StorageException;
import model.Resume;

import java.io.*;

public class ObjectStreamSerialization implements Serialization {
    @Override
    public boolean serialize(Resume resume, OutputStream os) throws IOException {
        try (final ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
            return true;
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
