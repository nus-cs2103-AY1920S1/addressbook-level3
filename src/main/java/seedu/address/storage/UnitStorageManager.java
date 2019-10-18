package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.storage.address.AddressBookStorage;
import seedu.address.storage.address.JsonAddressBookStorage;
import seedu.address.storage.event.AppointmentBookStorage;
import seedu.address.storage.event.JsonAppointmentBookStorage;

class UnitStorageManager implements AddressBookStorage, AppointmentBookStorage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final String targetUnitName;
    private final AddressBookStorage addressBookStorage;
    private final AppointmentBookStorage appointmentBookStorage;

    public UnitStorageManager(String targetUnitName) {
        this.targetUnitName = targetUnitName;
        this.addressBookStorage = new JsonAddressBookStorage();
        this.appointmentBookStorage = new JsonAppointmentBookStorage();
    }

    // ================ AddressBook methods ==============================

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath)
        throws DataConversionException, IOException {
        requireNonNull(filePath);
        logger.fine(String.format("Attempting to read %s data from file: %s", targetUnitName, filePath));
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath)
        throws IOException {
        requireNonNull(filePath);
        logger.fine(String.format("Attempting to write to %s data file: %s", targetUnitName, filePath));
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }


    // ================ AppointmentBook methods ==============================

    @Override
    public Optional<ReadOnlyAppointmentBook> readAppointmentBook(Path filePath)
        throws DataConversionException, IOException {
        requireNonNull(filePath);
        logger.fine(String.format("Attempting to read %s appointment data from file: %s", targetUnitName, filePath));
        return appointmentBookStorage.readAppointmentBook(filePath);
    }

    @Override
    public void saveAppointmentBook(ReadOnlyAppointmentBook appointmentsBook, Path filePath)
        throws IOException {
        requireNonNull(filePath);
        logger.fine(String.format("Attempting to write to %s patient data file: %s", targetUnitName, filePath));
        appointmentBookStorage.saveAppointmentBook(appointmentsBook, filePath);
    }

}
