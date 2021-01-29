package storage.filebased;

import exception.StorageException;
import model.Resume;
import storage.AbstractStorage;
import storage.serializer.Serialization;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final Serialization strategy;

    protected FileStorage(File directory, Serialization strategy) {
        this.strategy = strategy;
        Objects.requireNonNull(directory, "Directory must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        } else if (!(directory.canRead() || directory.canWrite())) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " in inaccessible");
        } else {
            this.directory = directory;
        }
    }

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
            strategy.serialize(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Could not update file ", file.getName());
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            if (file.createNewFile()) {
                strategy.serialize(resume, new BufferedOutputStream(new FileOutputStream(file)));
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
            return strategy.deserialize(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("Could not get file ", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Could not delete file", file.getName());
        }
    }

    @Override
    protected List<Resume> getAll() {
        List<Resume> results = new ArrayList<>();
        Arrays.stream(getListOfFiles(directory)).forEach(file -> {
            results.add(doGet(file));
        });
        return results;
    }

    @Override
    public void clear() {
        for (File file : getListOfFiles(directory)) {
            doDelete(file);
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.list(), "Directory is empty").length;
    }

    private File[] getListOfFiles(File directory) {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Could not get files from " + directory, null);
        }
        return files;
    }
}
