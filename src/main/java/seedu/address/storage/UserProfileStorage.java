package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserProfile;
import seedu.address.model.UserProfile;

/**
 * Represents a storage for {@link UserProfile}.
 */
public interface UserProfileStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getUserProfileFilePath();

    /**
     * Returns UserProfile data as a {@link ReadOnlyUserProfile}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyUserProfile> readUserProfile() throws DataConversionException, IOException;

    /**
     * @see #getUserProfileFilePath()
     */
    Optional<ReadOnlyUserProfile> readUserProfile(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyUserProfile} to the storage.
     * @param dukeCooks cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserProfile(ReadOnlyUserProfile dukeCooks) throws IOException;

    /**
     * @see #saveUserProfile(ReadOnlyUserProfile)
     */
    void saveUserProfile(ReadOnlyUserProfile dukeCooks, Path filePath) throws IOException;

}
