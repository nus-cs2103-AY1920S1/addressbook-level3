package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserState;

/**
 * Represents a storage for {@link seedu.address.model.Ledger}.
 */
public interface UserStateStorage {
    /**
     * Returns the file path of the data file.
     */
    Path getUserStateFilePath();

    /**
     * Returns UserState data as a {@link ReadOnlyUserState}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyUserState> readUserState() throws DataConversionException, IOException;

    /**
     * @see #getUserStateFilePath()
     */
    Optional<ReadOnlyUserState> readUserState(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyUserState} to the storage.
     *
     * @param userState cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserState(ReadOnlyUserState userState) throws IOException;

    /**
     * @see #saveUserState(ReadOnlyUserState)
     */
    void saveUserState(ReadOnlyUserState userState, Path filePath) throws IOException;
}
