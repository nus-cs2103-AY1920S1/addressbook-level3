package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.userprefs.ReadOnlyUserPrefs;
import seedu.address.model.userprefs.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage {

    /**
     * Gets a {@code UserPrefs} from the user prefs file,
     * or a new {@code UserPrefs} with default configuration if errors occur when
     * reading from the file.
     */
    UserPrefs getUserPrefs();

    /**
     * Saves the given {@link ReadOnlyUserPrefs} to the storage.
     *
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    /**
     * Returns the file path of the patient details data file.
     */
    Path getPatientAddressBookFilePath();

    /**
     * Returns the file path of the patient appointment data file.
     */
    Path getPatientAppointmentBookFilePath();

    /**
     * Returns the file path of the staff details data file.
     */
    Path getStaffAddressBookFilePath();

    /**
     * Returns the file path of the staff duty roster data file.
     */
    Path getStaffDutyRosterBookFilePath();

    /**
     * Returns patient AddressBook data as a {@link ReadOnlyAddressBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readPatientAddressBook() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the patient data storage.
     *
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePatientAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Returns patient AppointmentBook data as a {@link ReadOnlyAppointmentBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAppointmentBook> readPatientAppointmentBook() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAppointmentBook} to the patient data storage.
     *
     * @param appointmentBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePatientAppointmentBook(ReadOnlyAppointmentBook appointmentBook) throws IOException;

    /**
     * Returns staff AddressBook data as a {@link ReadOnlyAddressBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAddressBook> readStaffAddressBook() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the staff data storage.
     *
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStaffAddressBook(ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * Returns staff AppointmentBook data as a {@link ReadOnlyAppointmentBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAppointmentBook> readStaffDutyRosterBook() throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAppointmentBook} to the staff data storage.
     *
     * @param appointmentBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStaffDutyRosterBook(ReadOnlyAppointmentBook appointmentBook) throws IOException;
}
