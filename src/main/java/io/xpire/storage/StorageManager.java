package io.xpire.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import io.xpire.commons.core.LogsCenter;
import io.xpire.commons.exceptions.DataConversionException;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.ReadOnlyUserPrefs;
import io.xpire.model.UserPrefs;
import javafx.util.Pair;

/**
 * Manages storage of {@code Xpire} and {@code ReplenishList} data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private ListStorage listStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(ListStorage listStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.listStorage = listStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return this.userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return this.userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        this.userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ Xpire methods ==============================

    @Override
    public Path getListFilePath() {
        return this.listStorage.getListFilePath();
    }

    @Override
    public Pair<Optional<ReadOnlyListView>, Optional<ReadOnlyListView>> readList()
            throws DataConversionException, IOException {
        return readList(this.listStorage.getListFilePath());
    }

    @Override
    public Pair<Optional<ReadOnlyListView>, Optional<ReadOnlyListView>> readList(Path filePath) throws
            DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return this.listStorage.readList(filePath);
    }

    @Override
    public void saveList(ReadOnlyListView[] lists) throws IOException {
        saveList(lists, this.listStorage.getListFilePath());
    }

    @Override
    public void saveList(ReadOnlyListView[] lists, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        this.listStorage.saveList(lists, filePath);
    }

}
