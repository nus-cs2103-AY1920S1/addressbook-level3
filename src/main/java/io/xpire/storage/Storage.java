package io.xpire.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import io.xpire.commons.exceptions.DataConversionException;
import io.xpire.model.ReadOnlyListView;
import io.xpire.model.ReadOnlyUserPrefs;
import io.xpire.model.UserPrefs;
import io.xpire.model.item.Item;

/**
 * API of the Storage component
 */
public interface Storage extends ListStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getListFilePath();

    @Override
    Optional<ReadOnlyListView<? extends Item>>[] readList() throws DataConversionException, IOException;

    @Override
    void saveList(ReadOnlyListView<? extends Item>[] lists) throws IOException;

}
