package seedu.address.diaryfeature.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.diaryfeature.logic.parser.exceptions.DiaryEntryExceptions.DiaryEntryParseException;
import seedu.address.diaryfeature.model.DiaryBook;
import seedu.address.logic.parser.exceptions.ParseException;


public class JsonDiaryBookStorage implements DiaryBookStorage {
    private static final Logger logger = LogsCenter.getLogger(seedu.address.diaryfeature.storage.JsonDiaryBookStorage.class);
    private Path filePath;

    public JsonDiaryBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getDiaryBookFilePath() {
        return filePath;
    }

    @Override
    public void saveDiaryBook(DiaryBook diaryBook) throws IOException {
        saveDiaryBook(diaryBook, filePath);
    }

    /**
     * @param filePath location of the data. Cannot be null.
     */
    public void saveDiaryBook(DiaryBook diaryBook, Path filePath) throws IOException {
        requireNonNull(diaryBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableDiaryBook(diaryBook), filePath);
    }


    public Optional<DiaryBook> readDiaryBook() throws DataConversionException {
        return readDiaryBook(filePath);
    }

    /**
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<DiaryBook> readDiaryBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableDiaryBook> jsonDiaryBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableDiaryBook.class);
        if (jsonDiaryBook.isEmpty()) {
            logger.info("using empty book");
            return Optional.empty();
        }

        try {
            logger.info("using written book");
            return Optional.of(jsonDiaryBook.get().toModelType());

        } catch (ParseException error) {
            logger.info("Illegal values found in " + filePath + ": " + error);
            throw new DataConversionException(error);
        }
    }


}


