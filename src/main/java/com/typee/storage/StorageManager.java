package com.typee.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.commons.exceptions.DataConversionException;
import com.typee.model.ReadOnlyEngagementList;
import com.typee.model.ReadOnlyUserPrefs;
import com.typee.model.UserPrefs;
import com.typee.ui.Tab;

import javafx.collections.ObservableList;

/**
 * Manages storage of EngagementList data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private EngagementListStorage engagementListStorage;
    private UserPrefsStorage userPrefsStorage;
    //Adding TypeeStorage unit
    private TypeeStorage typeeStorage;

    public StorageManager(EngagementListStorage engagementListStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.engagementListStorage = engagementListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    public StorageManager(EngagementListStorage engagementListStorage, UserPrefsStorage userPrefsStorage,
                          TypeeStorage typeeStorage) {
        super();
        this.engagementListStorage = engagementListStorage;
        this.userPrefsStorage = userPrefsStorage;
        this.typeeStorage = typeeStorage;
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


    // ================ EngagementList methods ==============================

    @Override
    public Path getEngagementListFilePath() {
        return engagementListStorage.getEngagementListFilePath();
    }

    @Override
    public Optional<ReadOnlyEngagementList> readEngagementList() throws DataConversionException, IOException {
        return readEngagementList(engagementListStorage.getEngagementListFilePath());
    }

    @Override
    public Optional<ReadOnlyEngagementList> readEngagementList(Path filePath) throws DataConversionException,
            IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return engagementListStorage.readEngagementList(filePath);
    }

    @Override
    public void saveEngagementList(ReadOnlyEngagementList engagementList) throws IOException {
        saveEngagementList(engagementList, engagementListStorage.getEngagementListFilePath());
    }

    @Override
    public void saveEngagementList(ReadOnlyEngagementList engagementList, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        engagementListStorage.saveEngagementList(engagementList, filePath);
    }

    // ================ TYPEE methods ==============================
    @Override
    public ObservableList<Tab> getTabList() throws DataConversionException {
        logger.fine("Fetching tab list data from text file.");
        return typeeStorage.getTabList();
    }
}
