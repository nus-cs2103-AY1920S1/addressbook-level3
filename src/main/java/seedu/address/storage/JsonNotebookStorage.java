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
import seedu.address.model.ReadOnlyNotebook;

/**
 * A class to access Notebook data stored as a json file on the hard disk.
 */
public class JsonNotebookStorage implements NotebookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonNotebookStorage.class);

    private Path filePath;

    public JsonNotebookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getNotebookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyNotebook> readNotebook() throws DataConversionException {
        return readNotebook(filePath);
    }

    /**
     * Similar to {@link #readNotebook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyNotebook> readNotebook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableNotebook> jsonNotebook = JsonUtil.readJsonFile(
                filePath, JsonSerializableNotebook.class);
        if (jsonNotebook.isEmpty()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonNotebook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveNotebook(ReadOnlyNotebook notebook) throws IOException {
        saveNotebook(notebook, filePath);
    }

    /**
     * Similar to {@link #saveNotebook(ReadOnlyNotebook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveNotebook(ReadOnlyNotebook notebook, Path filePath) throws IOException {
        requireNonNull(notebook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableNotebook(notebook), filePath);
    }

}

