package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.Attendance;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyEvents;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.performance.Event;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private EventStorage eventStorage;
    private UserPrefsStorage userPrefsStorage;
    private AttendanceStorage attendanceStorage;


    public StorageManager(AddressBookStorage addressBookStorage, EventStorage eventStorage,
                          AttendanceStorage attendanceStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.eventStorage = eventStorage;
        this.attendanceStorage = attendanceStorage;
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


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }

    // ================ Performance methods ==============================

    @Override
    public Path getEventFilePath() {
        return eventStorage.getEventFilePath();
    }

    @Override
    public Optional<ReadOnlyEvents> readEvents() throws DataConversionException, IOException {
        return readEvents(eventStorage.getEventFilePath());
    }

    @Override
    public Optional<ReadOnlyEvents> readEvents(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return eventStorage.readEvents(filePath);
    }

    @Override
    public void saveEvents(ReadOnlyEvents events) throws IOException {
        saveEvents(events, eventStorage.getEventFilePath());
    }

    @Override
    public void saveEvents(ReadOnlyEvents events, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        eventStorage.saveEvents(events, filePath);
    }

    // ================ Attendance methods ==============================

    @Override
    public Optional<Attendance> readAttendance() throws DataConversionException, IOException {
        return attendanceStorage.readAttendance();
    }

    @Override
    public Optional<Attendance> readAttendance(Path filePath) throws DataConversionException, IOException {
        return attendanceStorage.readAttendance(filePath);
    }

    @Override
    public void saveAttendance(Attendance attendance) throws IOException {
        attendanceStorage.saveAttendance(attendance);
    }

    @Override
    public void saveAttendance(Attendance attendance, Path filePath) throws IOException {
        attendanceStorage.saveAttendance(attendance, filePath);
    }
}
