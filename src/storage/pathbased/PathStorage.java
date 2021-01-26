package storage.pathbased;

import exception.StorageException;
import model.Resume;
import storage.AbstractStorage;
import storage.serializer.StreamSerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathStorage extends AbstractStorage<Path> {
    private final Path path;
    private final StreamSerializer strategy;

    protected PathStorage(String path, StreamSerializer strategy) {
        this.path = Paths.get(path);
        this.strategy = strategy;
        Objects.requireNonNull(path, "Path must not be null");
        if (!Files.isDirectory(this.path)) {
            throw new IllegalArgumentException(this.path + " is not a directory");
        }
    }

    @Override
    protected boolean isPresent(Path resume) {
        return Files.exists(resume);
    }

    @Override
    protected Path getKey(String uuid) {
        return path.resolve(uuid);
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            strategy.serialize(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Could not update file ", path.toFile().getName());
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
            strategy.serialize(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Could not save file ", resume.getUuid(), e);
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return strategy.deserialize(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Could not get file ", path.toString(), e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Could not delete file", null, e);
        }
    }

    @Override
    protected List<Resume> getAll() {
        try {
            return Files.list(path)
                    .map(this::doGet)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new StorageException("Could not get list of files by " + path.toFile().getName(), null, e);
        }
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
            return (int) Files.list(path).count();
        } catch (IOException e) {
            throw new StorageException("Could not reach the directory", null, e);
        }
    }
}
