package seedu.address.storage.accommodation;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAccommodation;
import seedu.address.model.itineraryitem.accommodation.Accommodation;

/**
 * Represents a storage for {@link Accommodation}.
 */
public interface AccommodationStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAccommodationFilePath();

    /**
     * Returns Accommodation data as a {@link ReadOnlyAccommodation}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyAccommodation> readAccommodation() throws DataConversionException, IOException;

    /**
     * @see #getAccommodationFilePath()
     */
    Optional<ReadOnlyAccommodation> readAccommodation(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyAccommodation} to the storage.
     *
     * @param accommodationManager cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAccommodation(ReadOnlyAccommodation accommodationManager) throws IOException;

    /**
     * @see #saveAccommodation(ReadOnlyAccommodation)
     */
    void saveAccommodation(ReadOnlyAccommodation address, Path filePath) throws IOException;

}
