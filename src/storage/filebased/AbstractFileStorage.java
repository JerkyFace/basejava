package storage.filebased;

import exception.StorageException;
import model.Resume;
import storage.AbstractStorage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        } else if (!(directory.canRead() || directory.canWrite())) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " in inaccessible");
        } else {
            this.directory = directory;
        }
    }

    protected abstract boolean doWrite(Resume resume, File file);

    @Override
    protected boolean isPresent(File resume) {
        return resume.exists();
    }

    @Override
    protected File getKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected void doUpdate(Resume resume, File file) {

    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("Could not save file", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        Resume result;
        try {
            result = (Resume) Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new StorageException("Could not read the file", null, e);
        }
        return result;
    }

    @Override
    protected void doDelete(File file) {
        if (!file.isDirectory()) {
            file.delete();
        }
    }

    @Override
    protected List<Resume> getAll() {
        return null;
    }

    @Override
    public void clear() {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (!file.isDirectory()) {
                doDelete(file);
            }
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(new File(directory.getPath()).listFiles()).length;
    }
}
