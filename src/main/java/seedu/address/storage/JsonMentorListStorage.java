package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.entitylist.MentorList;

/**
 * A class to access MentorList data stored as a json file on the hard disk.
 */
public class JsonMentorListStorage implements MentorListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonMentorListStorage.class);

    private Path filePath;

    public JsonMentorListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getMentorListFilePath() {
        return filePath;
    }

    @Override
    public Optional<MentorList> readMentorList() throws DataConversionException, AlfredException {
        return readMentorList(filePath);
    }

    /**
     * Similar to {@link #readMentorList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<MentorList> readMentorList(Path filePath) throws DataConversionException, AlfredException {
        requireNonNull(filePath);

        Optional<JsonSerializableMentorList> jsonMentorList = JsonUtil.readJsonFile(
                filePath, JsonSerializableMentorList.class);
        if (!jsonMentorList.isPresent()) {
            return Optional.empty();
        }

        try {
            //Converts to Optional<MentorList>
            return Optional.of(jsonMentorList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        } catch (IllegalArgumentException iae) {
            logger.info("Illegal arguments found in " + filePath + ": " + iae.getMessage());
            throw new DataConversionException(iae);
        }
    }

    @Override
    public void saveMentorList(MentorList mList) throws IOException {
        saveMentorList(mList, filePath);
    }

    /**
     * Similar to {@link #saveMentorList(MentorList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveMentorList(MentorList mList, Path filePath) throws IOException {
        requireNonNull(mList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableMentorList(mList), filePath);
    }
}

