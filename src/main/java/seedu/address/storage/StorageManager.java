package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ModulesInfo;
import seedu.address.model.ReadOnlyModulePlanner;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of ModulePlanner data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ModulePlannerStorage modulePlannerStorage;
    private UserPrefsStorage userPrefsStorage;
    private ModulesInfoStorage modulesInfoStorage;

    public StorageManager(ModulePlannerStorage modulePlannerStorage, UserPrefsStorage userPrefsStorage,
                          ModulesInfoStorage modulesInfoStorage) {
        super();
        this.modulePlannerStorage = modulePlannerStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.modulesInfoStorage = modulesInfoStorage;
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


    // ================ ModulePlanner methods ==============================

    @Override
    public Path getModulePlannerFilePath() {
        return modulePlannerStorage.getModulePlannerFilePath();
    }

    @Override
    public Optional<ReadOnlyModulePlanner> readModulePlanner(ModulesInfo modulesInfo)
            throws DataConversionException, IOException {
        return readModulePlanner(modulePlannerStorage.getModulePlannerFilePath(), modulesInfo);
    }

    @Override
    public Optional<ReadOnlyModulePlanner> readModulePlanner(Path filePath, ModulesInfo modulesInfo)
            throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return modulePlannerStorage.readModulePlanner(filePath, modulesInfo);
    }

    @Override
    public void saveModulePlanner(ReadOnlyModulePlanner modulePlanner) throws IOException {
        saveModulePlanner(modulePlanner, modulePlannerStorage.getModulePlannerFilePath());
    }

    @Override
    public void saveModulePlanner(ReadOnlyModulePlanner modulePlanner, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        modulePlannerStorage.saveModulePlanner(modulePlanner, filePath);
    }

    // ================ ModulesInfo methods ==============================

    @Override
    public Path getModulesInfoPath() {
        return modulesInfoStorage.getModulesInfoPath();
    }

    @Override
    public Optional<ModulesInfo> readModulesInfo() throws DataConversionException, IOException {
        return modulesInfoStorage.readModulesInfo();
    }

}
