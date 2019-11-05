package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyTutorAid;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of TutorAid data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private TutorAidStorage tutorAidStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(TutorAidStorage tutorAidStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.tutorAidStorage = tutorAidStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ TutorAid methods ==============================

    @Override
    public Path getTutorAidFilePath() {
        return tutorAidStorage.getTutorAidFilePath();
    }

    @Override
    public Optional<ReadOnlyTutorAid> readTutorAid() throws DataConversionException, IOException {
        return readTutorAid(tutorAidStorage.getTutorAidFilePath());
    }

    @Override
    public Optional<ReadOnlyTutorAid> readTutorAid(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return tutorAidStorage.readTutorAid(filePath);
    }

    @Override
    public void saveTutorAid(ReadOnlyTutorAid tutorAid) throws IOException {
        saveTutorAid(tutorAid, tutorAidStorage.getTutorAidFilePath());
    }

    @Override
    public void saveTutorAid(ReadOnlyTutorAid tutorAid, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        tutorAidStorage.saveTutorAid(tutorAid, filePath);
    }

}
