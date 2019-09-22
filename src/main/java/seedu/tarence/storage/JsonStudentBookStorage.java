package seedu.tarence.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.commons.exceptions.DataConversionException;
import seedu.tarence.commons.exceptions.IllegalValueException;
import seedu.tarence.commons.util.FileUtil;
import seedu.tarence.commons.util.JsonUtil;
import seedu.tarence.model.ReadOnlyStudentBook;

/**
 * A class to access StudentBook data stored as a json file on the hard disk.
 */
public class JsonStudentBookStorage implements StudentBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonStudentBookStorage.class);

    private Path filePath;

    public JsonStudentBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getStudentBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyStudentBook> readStudentBook() throws DataConversionException {
        return readStudentBook(filePath);
    }

    /**
     * Similar to {@link #readStudentBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyStudentBook> readStudentBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableStudentBook> jsonStudentBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableStudentBook.class);
        if (!jsonStudentBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonStudentBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveStudentBook(ReadOnlyStudentBook studentBook) throws IOException {
        saveStudentBook(studentBook, filePath);
    }

    /**
     * Similar to {@link #saveStudentBook(ReadOnlyStudentBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveStudentBook(ReadOnlyStudentBook studentBook, Path filePath) throws IOException {
        requireNonNull(studentBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableStudentBook(studentBook), filePath);
    }

}
