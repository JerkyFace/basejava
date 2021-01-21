package storage.filebased;

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
            oos.flush();
            return true;
        }
    }

    @Override
    protected Resume doRead(InputStream file) throws IOException {
        return null;
    }
}
