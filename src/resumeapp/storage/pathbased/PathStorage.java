package resumeapp.storage.pathbased;

import resumeapp.exception.StorageException;
import resumeapp.model.Resume;
import resumeapp.storage.AbstractStorage;
import resumeapp.storage.serializer.StreamSerializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
            throw new StorageException("Could not update file ", resume.getUuid());
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
            doUpdate(resume, path);
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
        return getList(path)
                .map(this::doGet)
                .collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getList(path).forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getList(path).count();
    }

    private Stream<Path> getList(Path path) {
        try {
            return Files.list(path);
        } catch (IOException e) {
            throw new StorageException("Could not reach the directory", null, e);
        }
    }
}
