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
import seedu.address.model.ReadOnlyFinSec;

/**
 * A class to access FinSec data stored as a json file on the hard disk.
 */
public class JsonFinSecStorage implements FinSecStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFinSecStorage.class);

    private Path filePath;

    public JsonFinSecStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getFinSecFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyFinSec> readContacts() throws DataConversionException {
        return readContacts(filePath);
    }

    /**
     * Similar to {@link #readContacts()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyFinSec> readContacts(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableFinSec> jsonFinSec = JsonUtil.readJsonFile(
                filePath, JsonSerializableFinSec.class);
        if (!jsonFinSec.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFinSec.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    public void saveFinSec(ReadOnlyFinSec finSec) throws IOException {
        saveFinSec(finSec, filePath);
    }

    /**
     * Similar to {@link #saveFinSec(ReadOnlyFinSec)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveFinSec(ReadOnlyFinSec finSec, Path filePath) throws IOException {
        requireNonNull(finSec);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFinSec(finSec), filePath);
    }

}
