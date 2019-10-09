package com.typee.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.typee.commons.exceptions.DataConversionException;
import com.typee.model.ReadOnlyAddressBook;
import com.typee.model.ReadOnlyUserPrefs;
import com.typee.model.Tab;
import com.typee.model.UserPrefs;

import javafx.collections.ObservableList;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage, TypeeStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    @Override
    ObservableList<Tab> getTabList() throws DataConversionException;
}
