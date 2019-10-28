package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyClassroom;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of Classroom data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ClassroomStorage classroomStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ClassroomStorage classroomStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.classroomStorage = classroomStorage;
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


    // ================ Classroom methods ==============================

    @Override
    public Path getClassroomFilePath() {
        return classroomStorage.getClassroomFilePath();
    }

    @Override
    public Optional<ReadOnlyClassroom> readClassroom() throws DataConversionException, IOException {
        return readClassroom(classroomStorage.getClassroomFilePath());
    }

    @Override
    public Optional<ReadOnlyClassroom> readClassroom(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return classroomStorage.readClassroom(filePath);
    }

    @Override
    public void saveClassroom(ReadOnlyClassroom classroom) throws IOException {
        saveClassroom(classroom, classroomStorage.getClassroomFilePath());
    }

    @Override
    public void saveClassroom(ReadOnlyClassroom classroom, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        classroomStorage.saveClassroom(classroom, filePath);
    }

}
