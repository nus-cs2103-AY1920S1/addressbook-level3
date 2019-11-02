package seedu.address.diaryfeature.storage;

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
import seedu.address.diaryfeature.model.DiaryBook;
import seedu.address.diaryfeature.model.exceptions.TitleException;

import java.text.ParseException;

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
         *
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
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<DiaryBook> readDiaryBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableDiaryBook> jsonDiaryBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableDiaryBook.class);
        if (!jsonDiaryBook.isPresent()) {
            logger.info("using empty book");
            return Optional.empty();
        }

        try {
            logger.info("using written book");
            return Optional.of(jsonDiaryBook.get().toModelType());

        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        } catch(ParseException parseError) {
            logger.info("Parse Exception in date format");
        } catch (TitleException titEr) {
            logger.info("something went wrong with the title");
        }
        return null;
    }


}


