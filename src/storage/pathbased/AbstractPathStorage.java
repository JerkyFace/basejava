package storage.pathbased;

import exception.StorageException;
import model.Resume;
import storage.AbstractStorage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class AbstractPathStorage extends AbstractStorage<Path> {
    private final Path path;

    protected AbstractPathStorage(String path) {
        this.path = Paths.get(path);
        Objects.requireNonNull(path, "Path must not be null");
        if (!Files.isDirectory(this.path)) {
            throw new IllegalArgumentException(this.path + " is not a directory");
        }
    }

    @Override
    protected boolean isPresent(Path resume) {
        return resume.toFile().isFile();
    }

    @Override
    protected Path getKey(String uuid) {
        return Paths.get(uuid);
    }

    @Override
    protected void doUpdate(Resume resume, Path key) {

    }

    @Override
    protected void doSave(Resume resume, Path key) {

    }

    @Override
    protected Resume doGet(Path key) {
        return null;
    }

    @Override
    protected void doDelete(Path key) {

    }

    @Override
    protected List<Resume> getAll() {
        return null;
    }

    @Override
    public void clear() {
        try {
            Files.list(path).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Could not clear the directory", null, e);
        }
    }

    @Override
    public int size() {
        try {
            return (int) Files.walk(path).count();
        } catch (IOException e) {
            throw new StorageException("Could not reach the directory", null, e);
        }
    }
}
