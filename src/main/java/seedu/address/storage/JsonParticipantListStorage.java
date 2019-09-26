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

/**
 * A class to access ParticipantList data stored as a json file on the hard disk.
 */
public class JsonParticipantListStorage implements ParticipantListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonParticipantListStorage.class);

    private Path filePath;

    public JsonParticipantListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getParticipantListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ParticipantList> readParticipantList() throws DataConversionException {
        return readParticipantList(filePath);
    }

    /**
     * Similar to {@link #readParticipantList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ParticipantList> readParticipantList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableParticipantList> jsonParticipantList = JsonUtil.readJsonFile(
                filePath, JsonSerializableParticipantList.class);
        if (!jsonParticipantList.isPresent()) {
            return Optional.empty();
        }

        try {
            //Converts to Optional<ParticipantList>
            return Optional.of(jsonParticipantList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveParticipantList(ParticipantList pList) throws IOException {
        saveParticipantList(pList, filePath);
    }

    /**
     * Similar to {@link #saveParticipantList(ParticipantList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveParticipantList(ParticipantList pList, Path filePath) throws IOException {
        requireNonNull(pList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableParticipantList(pList), filePath);
    }
}

