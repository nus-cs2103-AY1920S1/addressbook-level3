package seedu.planner.storage.contact;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.commons.exceptions.DataConversionException;
import seedu.planner.commons.exceptions.IllegalValueException;
import seedu.planner.commons.util.FileUtil;
import seedu.planner.commons.util.JsonUtil;
import seedu.planner.model.ReadOnlyContact;

/**
 * A class to access Contact data stored as a json file on the hard disk.
 */
public class JsonContactStorage implements ContactStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonContactStorage.class);

    private Path filePath;

    public JsonContactStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getContactFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyContact> readContact() throws DataConversionException {
        return readContact(filePath);
    }

    /**
     * Similar to {@link #readContact}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyContact> readContact(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableContact> jsonContact = JsonUtil.readJsonFile(
                filePath, JsonSerializableContact.class);
        if (!jsonContact.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonContact.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveContact(ReadOnlyContact contact) throws IOException {
        saveContact(contact, filePath);
    }

    /**
     * Similar to {@link #saveContact(ReadOnlyContact)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveContact(ReadOnlyContact contact, Path filePath) throws IOException {
        requireNonNull(contact);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableContact(contact), filePath);
    }

}
