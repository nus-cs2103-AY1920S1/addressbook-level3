package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.CardBook;

/**
 * Represents a storage for {@link seedu.address.model.CardBook}.
 */
public interface CardBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCardBookFilePath();

    /**
     * Returns CardBook data as a {@link CardBook}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<CardBook> readCardBook() throws DataConversionException, IOException;

    /**
     * @see #getCardBookFilePath()
     */
    Optional<CardBook> readCardBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link CardBook} to the storage.
     * @param cardBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveCardBook(CardBook cardBook) throws IOException;

    /**
     * @see #saveCardBook(CardBook)
     */
    void saveCardBook(CardBook cardBook, Path filePath) throws IOException;

}
