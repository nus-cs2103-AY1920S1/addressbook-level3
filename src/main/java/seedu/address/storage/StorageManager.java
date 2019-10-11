package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyProjectDashboard;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of ProjectDashboard data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ProjectDashboardStorage projectDashboardStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ProjectDashboardStorage projectDashboardStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.projectDashboardStorage = projectDashboardStorage;
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


    // ================ ProjectDashboard methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return projectDashboardStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyProjectDashboard> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(projectDashboardStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyProjectDashboard> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return projectDashboardStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyProjectDashboard addressBook) throws IOException {
        saveAddressBook(addressBook, projectDashboardStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyProjectDashboard addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        projectDashboardStorage.saveAddressBook(addressBook, filePath);
    }

}
