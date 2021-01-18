package storage.filebased;

import exception.StorageException;
import model.Resume;
import storage.AbstractStorage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {
    private final File directory;
    private int size;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        } else if (!(directory.canRead() || directory.canWrite())) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " in inaccessible");
        } else {
            size = 0;
            this.directory = directory;
        }
    }

    protected abstract boolean doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file) throws IOException;

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
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("Could not update file ", file.getName());
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            if (file.createNewFile()) {
                doWrite(resume, file);
                size++;
            } else {
                throw new StorageException("Could not create file ", file.getName());
            }
        } catch (IOException e) {
            throw new StorageException("Could not save file ", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return doRead(file);
        } catch (IOException e) {
            throw new StorageException("Could not get file ", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.isFile()) {
            throw new StorageException(file.getPath() + " is not a file", null);
        } else if (!file.delete()) {
            throw new StorageException("Could not delete file", file.getName());
        } else {
            size--;
        }
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> results = new ArrayList<>();
        File[] files = directory.listFiles();
        if (files != null) {
            Arrays.stream(files).forEach(file -> {
                results.add(doGet(file));
            });
        } else {
            throw new StorageException("Could not get files from " + directory, null);
        }
        return results;
    }

    @Override
    public void clear() {
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            doDelete(file);
        }
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }
}
