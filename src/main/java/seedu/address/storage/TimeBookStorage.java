package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.TimeBook;

/**
 * Represents a storage for TimeBook.
 */
public interface TimeBookStorage {

    Path getTimeBookFilePath();

    Optional<TimeBook> readTimeBook() throws DataConversionException, IOException;

    Optional<TimeBook> readTimeBook(Path filePath) throws DataConversionException, IOException;

    void saveTimeBook(TimeBook timeBook) throws IOException;

    void saveTimeBook(TimeBook timeBook, Path filePath) throws IOException;
}
