package storage.filebased;

import exception.StorageException;
import model.Resume;

import java.io.*;

public class ObjectStreamStorage extends AbstractFileStorage {
    protected ObjectStreamStorage(File directory) {
        super(directory);
    }

    @Override
    protected boolean doWrite(Resume resume, OutputStream file) throws IOException {
        try (final ObjectOutputStream oos = new ObjectOutputStream(file)) {
            oos.writeObject(resume);
            return true;
        }
    }

    @Override
    protected Resume doRead(InputStream file) throws IOException {
        try (final ObjectInputStream ois = new ObjectInputStream(file)) {
            return (Resume) ois.readObject();
        } catch (ClassNotFoundException cnf) {
            throw new StorageException("Could not read resume from file", null, cnf);
        }
    }
}
