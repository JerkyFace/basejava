package resumeapp.storage;

import resumeapp.exception.ExistStorageException;
import resumeapp.exception.NotExistStorageException;
import resumeapp.exception.StorageException;
import resumeapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<SK> implements Storage {

    private static final Logger LOGGER = Logger.getLogger(AbstractStorage.class.getName());

    protected abstract boolean isPresent(SK resume);

    protected abstract SK getKey(String uuid);

    protected abstract void doUpdate(Resume resume, SK key);

    protected abstract void doSave(Resume resume, SK key);

    protected abstract Resume doGet(SK key);

    protected abstract void doDelete(SK key);

    protected abstract List<Resume> getAll();

    @Override
    public void update(Resume resume) {
        if (resume == null) {
            throw new StorageException("Cannot save empty resume");
        }
        doUpdate(resume, getIfPresent(resume.getUuid()));
        LOGGER.info("Update: " + resume);
    }

    @Override
    public void save(Resume resume) {
        if (resume == null) {
            throw new StorageException("Cannot save empty resume");
        }
        SK key = getKey(resume.getUuid());
        LOGGER.info("Save: got key " + key);
        if (isPresent(key)) {
            throw new ExistStorageException(resume.getUuid());
        }
        doSave(resume, key);
        LOGGER.info("saved on " + key);
    }

    @Override
    public Resume get(String uuid) {
        return doGet(getIfPresent(uuid));
    }

    @Override
    public void delete(String uuid) {
        doDelete(getIfPresent(uuid));
        LOGGER.info("Delete: deleted by " + uuid);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = getAll();
        result.sort(Comparator.comparing(Resume::getUuid).thenComparing(Resume::getFullName));
        LOGGER.info("GetAllSorted(): successful");
        return result;
    }

    private SK getIfPresent(String uuid) {
        SK key = getKey(uuid);
        if (!isPresent(key)) {
            LOGGER.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        LOGGER.info("getIfPreset(): gotten by " + uuid);
        return key;
    }
}
