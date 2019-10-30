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
import seedu.address.model.Notebook;

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
    public Optional<Notebook> readNotebook() throws DataConversionException {
        return readNotebook(filePath);
    }

    /**
     * Similar to {@link #readNotebook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<Notebook> readNotebook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableNotebook> jsonNotebook = JsonUtil.readJsonFile(
                filePath, JsonSerializableNotebook.class);
        if (!jsonNotebook.isPresent()) {
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
    public void saveNotebook(Notebook classroom) throws IOException {
        saveNotebook(classroom, filePath);
    }

    /**
     * Similar to {@link #saveNotebook(Notebook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveNotebook(Notebook classroom, Path filePath) throws IOException {
        requireNonNull(classroom);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableNotebook(classroom), filePath);
    }

}

