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
 * A class to access IssueList data stored as a json file on the hard disk.
 */
public class JsonIssueListStorage implements IssueListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonIssueListStorage.class);

    private Path filePath;

    public JsonIssueListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getIssueListFilePath() {
        return filePath;
    }

    @Override
    public Optional<IssueList> readIssueList() throws DataConversionException {
        return readIssueList(filePath);
    }

    /**
     * Similar to {@link #readIssueList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<IssueList> readIssueList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableIssueList> jsonIssueList = JsonUtil.readJsonFile(
                filePath, JsonSerializableIssueList.class);
        if (!jsonIssueList.isPresent()) {
            return Optional.empty();
        }

        try {
            //Converts to Optional<IssueList>
            return Optional.of(jsonIssueList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveIssueList(IssueList iList) throws IOException {
        saveIssueList(iList, filePath);
    }

    /**
     * Similar to {@link #saveIssueList(IssueList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveIssueList(IssueList iList, Path filePath) throws IOException {
        requireNonNull(iList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableIssueList(iList), filePath);
    }
}

