package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyUserList;

/**
 * Represents a storage for {@link seedu.address.model.UserList}.
 */
public interface UserListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getUserListFilePath();

    /**
     * Returns UserList data as a {@link ReadOnlyUserList}. Returns {@code Optional.empty()} if storage file is not
     * found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyUserList> readUserList() throws DataConversionException, IOException;

    /**
     * @see #getUserListFilePath()
     */
    Optional<ReadOnlyUserList> readUserList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyUserList} to the storage.
     *
     * @param userList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserList(ReadOnlyUserList userList) throws IOException;

    /**
     * @see #saveUserList(ReadOnlyUserList)
     */
    void saveUserList(ReadOnlyUserList userList, Path filePath) throws IOException;

    /**
     * Return a list of maps of fields in the json file that contain invalid references.
     *
     * @return List of maps of fields in the json file containing invalid references.
     */
    List<Map<String, String>> getListOfFieldsContainingInvalidReferences();


}
