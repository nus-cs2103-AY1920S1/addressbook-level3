package seedu.address.storage.bio;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyUserList;
import seedu.address.storage.UserListStorage;

/**
 * A class to access UserList data stored as a json file on the hard disk.
 */
public class JsonUserListStorage implements UserListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonUserListStorage.class);

    private Path filePath;
    private List<Map<String, String>> listOfFieldsContainingInvalidReferences = new ArrayList<>();

    public JsonUserListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getUserListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyUserList> readUserList() throws DataConversionException {
        return readUserList(filePath);
    }

    /**
     * Similar to {@link #readUserList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyUserList> readUserList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableUserList> jsonUserList = JsonUtil.readJsonFile(
            filePath, JsonSerializableUserList.class);
        if (!jsonUserList.isPresent()) {
            return Optional.empty();
        }

        listOfFieldsContainingInvalidReferences = JsonSerializableUserList
            .getListOfFieldsContainingInvalidReferences();

        try {
            return Optional.of(jsonUserList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUserList(ReadOnlyUserList userList) throws IOException {
        saveUserList(userList, filePath);
    }

    /**
     * Similar to {@link #saveUserList(ReadOnlyUserList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveUserList(ReadOnlyUserList userList, Path filePath) throws IOException {
        requireNonNull(userList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableUserList(userList), filePath);
    }

    @Override
    public List<Map<String, String>> getListOfFieldsContainingInvalidReferences() {
        return listOfFieldsContainingInvalidReferences;
    }
}
