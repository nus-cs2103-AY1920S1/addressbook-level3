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
import seedu.address.model.ReadOnlyAthletick;

/**
 * A class to access Athletick data stored as a json file on the hard disk.
 */
public class JsonAthletickStorage implements AthletickStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAthletickStorage.class);

    private Path filePath;

    public JsonAthletickStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAthletickFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyAthletick> readAthletick() throws DataConversionException {
        return readAthletick(filePath);
    }

    /**
     * Similar to {@link #readAthletick()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAthletick> readAthletick(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAthletick> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAthletick.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveAthletick(ReadOnlyAthletick athletick) throws IOException {
        saveAthletick(athletick, filePath);
    }

    /**
     * Similar to {@link #saveAthletick(ReadOnlyAthletick)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAthletick(ReadOnlyAthletick athletick, Path filePath) throws IOException {
        requireNonNull(athletick);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAthletick(athletick), filePath);
    }

}
