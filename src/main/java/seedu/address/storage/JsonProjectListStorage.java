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
import seedu.address.model.ReadOnlyProjectList;

/**
 * A class to access ProjectList data stored as a json file on the hard disk.
 */
public class JsonProjectListStorage implements ProjectListStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonProjectListStorage.class);

    private Path filePath;

    public JsonProjectListStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getProjectListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyProjectList> readProjectList() throws DataConversionException {
        return readProjectList(filePath);
    }

    /**
     * Similar to {@link #readProjectList()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyProjectList> readProjectList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableProjectList> jsonProjectList = JsonUtil.readJsonFile(
                filePath, JsonSerializableProjectList.class);
        if (jsonProjectList.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonProjectList.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveProjectList(ReadOnlyProjectList ProjectList) throws IOException {
        saveProjectList(ProjectList, filePath);
    }

    /**
     * Similar to {@link #saveProjectList(ReadOnlyProjectList)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveProjectList(ReadOnlyProjectList projectList, Path filePath) throws IOException {
        requireNonNull(projectList);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableProjectList(projectList), filePath);
    }

}
