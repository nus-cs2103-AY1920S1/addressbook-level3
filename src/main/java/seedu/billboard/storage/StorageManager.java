package seedu.billboard.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.billboard.commons.core.LogsCenter;
import seedu.billboard.commons.exceptions.DataConversionException;
import seedu.billboard.model.ReadOnlyBillboard;
import seedu.billboard.model.ReadOnlyUserPrefs;
import seedu.billboard.model.UserPrefs;

/**
 * Manages storage of Billboard data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private BillboardStorage billboardStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(BillboardStorage billboardStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.billboardStorage = billboardStorage;
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


    // ================ Billboard methods ==============================

    @Override
    public Path getBillboardFilePath() {
        return billboardStorage.getBillboardFilePath();
    }

    @Override
    public Optional<ReadOnlyBillboard> readBillboard() throws DataConversionException, IOException {
        return readBillboard(billboardStorage.getBillboardFilePath());
    }

    @Override
    public Optional<ReadOnlyBillboard> readBillboard(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return billboardStorage.readBillboard(filePath);
    }

    @Override
    public void saveBillboard(ReadOnlyBillboard addressBook) throws IOException {
        saveBillboard(addressBook, billboardStorage.getBillboardFilePath());
    }

    @Override
    public void saveBillboard(ReadOnlyBillboard addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        billboardStorage.saveBillboard(addressBook, filePath);
    }

}
