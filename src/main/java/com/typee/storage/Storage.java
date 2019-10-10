package com.typee.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.typee.commons.exceptions.DataConversionException;
import com.typee.model.ReadOnlyAppointmentList;
import com.typee.model.ReadOnlyUserPrefs;
import com.typee.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyAppointmentList> readAddressBook() throws DataConversionException, IOException;

    @Override
    void saveAddressBook(ReadOnlyAppointmentList addressBook) throws IOException;

}
