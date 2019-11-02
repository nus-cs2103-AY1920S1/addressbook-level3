package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyUserState;

/**
 * A class to access UserState data stored as a json file on the hard disk.
 */
public class JsonUserStateStorage implements UserStateStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonUserStateStorage.class);

    private Path filePath;

    public JsonUserStateStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getUserStateFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyUserState> readUserState() throws DataConversionException, IOException {
        return readUserState(filePath);
    }

    @Override
    public Optional<ReadOnlyUserState> readUserState(Path filePath) throws DataConversionException, IOException {
        requireNonNull(filePath);

        Optional<JsonSerializableUserState> jsonUserState = JsonUtil.readJsonFile(
            filePath, JsonSerializableUserState.class
        );
        if (!jsonUserState.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonUserState.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveUserState(ReadOnlyUserState userState) throws IOException {
        saveUserState(userState, filePath);
    }

    @Override
    public void saveUserState(ReadOnlyUserState userState, Path filePath) throws IOException {
        requireNonNull(userState);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableUserState(userState), filePath);
    }

}

