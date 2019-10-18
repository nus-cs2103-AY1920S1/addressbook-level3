package com.typee.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.typee.commons.exceptions.DataConversionException;
import com.typee.model.ReadOnlyEngagementList;
import com.typee.model.ReadOnlyUserPrefs;
import com.typee.model.UserPrefs;
import com.typee.ui.Tab;

import javafx.collections.ObservableList;

/**
 * API of the Storage component
 */
public interface Storage extends EngagementListStorage, TypeeStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getEngagementListFilePath();

    @Override
    Optional<ReadOnlyEngagementList> readEngagementList() throws DataConversionException, IOException;

    @Override
    void saveEngagementList(ReadOnlyEngagementList engagementList) throws IOException;

    @Override
    ObservableList<Tab> getTabList() throws DataConversionException;
}
