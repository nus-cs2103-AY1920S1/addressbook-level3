package com.typee.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.typee.commons.exceptions.DataConversionException;
import com.typee.model.AppointmentList;
import com.typee.model.ReadOnlyAppointmentList;

/**
 * Represents a storage for {@link AppointmentList}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link ReadOnlyAppointmentList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAppointmentList> readAddressBook() throws DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<ReadOnlyAppointmentList> readAddressBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAppointmentList} to the storage.
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(ReadOnlyAppointmentList addressBook) throws IOException;

    /**
     * @see #saveAddressBook(ReadOnlyAppointmentList)
     */
    void saveAddressBook(ReadOnlyAppointmentList addressBook, Path filePath) throws IOException;

}
