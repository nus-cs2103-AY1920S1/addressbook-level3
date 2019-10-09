package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAppointmentBook;

/**
 * Represents a storage for {@link AppointmentBook}.
 */
public interface AppointmentBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAppointmentBookFilePath();

    /**
     * Returns AppointmentBook data as a {@link ReadOnlyAppointmentBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAppointmentBook> readAppointmentBook() throws DataConversionException, IOException;

    /**
     * @see #getAppointmentBookFilePath()
     */
    Optional<ReadOnlyAppointmentBook> readAppointmentBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAppointmentBook} to the storage.
     * @param appointmentBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook) throws IOException;

    /**
     * @see #saveAppointmentBook(ReadOnlyAppointmentBook)
     */
    void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook, Path filePath) throws IOException;

}
