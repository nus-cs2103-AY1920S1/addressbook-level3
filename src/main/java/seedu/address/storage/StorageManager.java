package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.userprefs.ReadOnlyUserPrefs;
import seedu.address.model.userprefs.UserPrefs;
import seedu.address.storage.address.AddressBookStorage;
import seedu.address.storage.address.JsonAddressBookStorage;
import seedu.address.storage.event.AppointmentBookStorage;
import seedu.address.storage.event.JsonAppointmentBookStorage;
import seedu.address.storage.userprefs.JsonUserPrefsStorage;
import seedu.address.storage.userprefs.UserPrefsStorage;


/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final AddressBookStorage patientAddressBookStorage;
    private final AddressBookStorage staffAddressBookStorage;
    private final AppointmentBookStorage patientAppointmentBookStorage;
    private final AppointmentBookStorage staffDutyRosterBookStorage;
    private final UserPrefsStorage userPrefsStorage;
    private final UserPrefs userPrefs;

    public StorageManager(Path userPrefsFilePath) {
        requireNonNull(userPrefsFilePath);
        this.userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
        this.patientAddressBookStorage = new JsonAddressBookStorage();
        this.staffAddressBookStorage = new JsonAddressBookStorage();
        this.patientAppointmentBookStorage = new JsonAppointmentBookStorage();
        this.staffDutyRosterBookStorage = new JsonAppointmentBookStorage();

        this.userPrefs = loadUserPrefs();
    }

    private UserPrefs loadUserPrefs() {
        Path prefsFilePath = userPrefsStorage.getUserPrefsFilePath();
        logger.info("Using prefs file : " + prefsFilePath);

        UserPrefs initialUserPrefs = null;
        try {
            initialUserPrefs = userPrefsStorage.readUserPrefs().orElseThrow(() -> new IOException());
        } catch (DataConversionException e) {
            logger.warning("UserPrefs file at " + prefsFilePath + " is not in the correct format. "
                + "Using default user prefs");
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
        }

        if (initialUserPrefs == null) {
            initialUserPrefs = new UserPrefs();
        }

        //Update prefs file in case it was missing to begin with or there are new/unused fields
        try {
            userPrefsStorage.saveUserPrefs(initialUserPrefs);
        } catch (IOException e) {
            logger.warning("Failed to save config file : " + StringUtil.getDetails(e));
        }

        return initialUserPrefs;
    }

    @Override
    public UserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }

    @Override
    public Path getPatientAddressBookFilePath() {
        return getUserPrefs().getPatientAddressBookFilePath();
    }

    @Override
    public Path getPatientAppointmentBookFilePath() {
        return getUserPrefs().getPatientAppointmentBookFilePath();
    }

    @Override
    public Path getStaffAddressBookFilePath() {
        return getUserPrefs().getStaffAddressBookFilePath();
    }

    @Override
    public Path getStaffDutyRosterBookFilePath() {
        return getUserPrefs().getDutyRosterBookFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readPatientAddressBook()
        throws DataConversionException, IOException {
        return patientAddressBookStorage.readAddressBook(getPatientAddressBookFilePath());
    }

    @Override
    public void savePatientAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        logger.fine("Attempting to write to data file: " + getPatientAddressBookFilePath());
        patientAddressBookStorage.saveAddressBook(addressBook, getPatientAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAppointmentBook> readPatientAppointmentBook()
        throws DataConversionException, IOException {
        return patientAppointmentBookStorage.readAppointmentBook(getPatientAppointmentBookFilePath());
    }

    @Override
    public void savePatientAppointmentBook(ReadOnlyAppointmentBook appointmentBook) throws IOException {
        logger.fine("Attempting to write to data file: " + getPatientAppointmentBookFilePath());
        patientAppointmentBookStorage.saveAppointmentBook(appointmentBook, getPatientAppointmentBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readStaffAddressBook() throws DataConversionException, IOException {
        return staffAddressBookStorage.readAddressBook(getStaffAddressBookFilePath());
    }

    @Override
    public void saveStaffAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        logger.fine("Attempting to write to data file: " + getStaffAddressBookFilePath());
        staffAddressBookStorage.saveAddressBook(addressBook, getStaffAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAppointmentBook> readStaffDutyRosterBook() throws DataConversionException, IOException {
        return staffDutyRosterBookStorage.readAppointmentBook(getStaffDutyRosterBookFilePath());
    }

    @Override
    public void saveStaffDutyRosterBook(ReadOnlyAppointmentBook dutyRosterBook) throws IOException {
        logger.fine("Attempting to write to data file: " + getStaffDutyRosterBookFilePath());
        staffDutyRosterBookStorage.saveAppointmentBook(dutyRosterBook, getStaffDutyRosterBookFilePath());
    }
}
