package seedu.exercise.storage.bookstorage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.exercise.commons.core.LogsCenter;
import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.commons.util.FileUtil;
import seedu.exercise.commons.util.JsonUtil;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.storage.serializablebook.JsonSerializableRegimeBook;

/**
 * A class to access RegimeBook data stored as a json file on the hard disk.
 */
public class JsonRegimeBookStorage implements ResourceBookStorage<Regime> {
    private static final Logger logger = LogsCenter.getLogger(JsonRegimeBookStorage.class);

    private Path filePath;

    public JsonRegimeBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getResourceBookFilePath() {
        return this.filePath;
    }

    @Override
    public Optional<ReadOnlyResourceBook<Regime>> readResourceBook() throws DataConversionException {
        return readResourceBook(filePath);
    }

    @Override
    public Optional<ReadOnlyResourceBook<Regime>> readResourceBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableRegimeBook> jsonRegimeBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableRegimeBook.class);

        if (!jsonRegimeBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonRegimeBook.get().toModelType(Regime.class));
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveResourceBook(ReadOnlyResourceBook<Regime> regimeBook) throws IOException {
        saveResourceBook(regimeBook, filePath);
    }

    @Override
    public void saveResourceBook(ReadOnlyResourceBook<Regime> regimeBook, Path filePath) throws IOException {
        requireNonNull(regimeBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableRegimeBook(regimeBook), filePath);
    }
}
