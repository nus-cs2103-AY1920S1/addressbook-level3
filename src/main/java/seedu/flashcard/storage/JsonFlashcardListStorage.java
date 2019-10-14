package seedu.flashcard.storage;

import static java.util.Objects.requireNonNull;

import seedu.flashcard.commons.exceptions.IllegalValueException;
import seedu.flashcard.commons.util.FileUtil;
import seedu.flashcard.commons.util.JsonUtil;
import seedu.flashcard.commons.core.LogsCenter;
import seedu.flashcard.commons.exceptions.DataConversionException;
import seedu.flashcard.model.ReadOnlyFlashcardList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access FlashcardList data stored as a json file on the hard disk.
 */
public class JsonFlashcardListStorage implements FlashcardStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonFlashcardListStorage.class);

    private Path filePath;

    public JsonFlashcardListStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getFlashcardListFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyFlashcardList> readFlashcardList() throws DataConversionException {
        return readFlashcardList(filePath);
    }

    /**
     * Similar to {@link #readFlashcardList()}.
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    @Override
    public Optional<ReadOnlyFlashcardList> readFlashcardList(Path filePath) throws DataConversionException {
        requireNonNull(filePath);
        Optional<JsonSerializableFlashcardList> jsonFlashcardList
                = JsonUtil.readJsonFile(filePath, JsonSerializableFlashcardList.class);
        if (!jsonFlashcardList.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonFlashcardList.get().toModelType());
        } catch (IllegalValueException e) {
            logger.info("Illegal values found in " + filePath + ": " + e.getMessage());
            throw new DataConversionException(e);
        }
    }

    @Override
    public void saveFlashcardList(ReadOnlyFlashcardList flashcardList) throws IOException {
        saveFlashcardList(flashcardList, filePath);
    }

    /**
     * Similar to {@link #saveFlashcardList(ReadOnlyFlashcardList)}.
     * @param filePath location of the data. Cannot be null.
     */
    @Override
    public void saveFlashcardList(ReadOnlyFlashcardList flashcardList, Path filePath) throws IOException {
        requireNonNull(flashcardList);
        requireNonNull(filePath);
        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableFlashcardList(flashcardList), filePath);
    }
}
