package seedu.guilttrip.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.commons.exceptions.DataConversionException;
import seedu.guilttrip.commons.exceptions.IllegalValueException;
import seedu.guilttrip.commons.util.FileUtil;
import seedu.guilttrip.commons.util.JsonUtil;
import seedu.guilttrip.model.ReadOnlyGuiltTrip;

/**
 * A class to access GuiltTrip data stored as a json file on the hard disk.
 */
public class JsonGuiltTripStorage implements GuiltTripStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonGuiltTripStorage.class);

    private Path filePath;

    public JsonGuiltTripStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getGuiltTripFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyGuiltTrip> readGuiltTrip() throws DataConversionException {
        return readGuiltTrip(filePath);
    }

    /**
     * Similar to {@link #readGuiltTrip()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyGuiltTrip> readGuiltTrip(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableGuiltTrip> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableGuiltTrip.class);
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
    public void saveGuiltTrip(ReadOnlyGuiltTrip addressBook) throws IOException {
        saveGuiltTrip(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveGuiltTrip(ReadOnlyGuiltTrip)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveGuiltTrip(ReadOnlyGuiltTrip addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableGuiltTrip(addressBook), filePath);
    }

}
