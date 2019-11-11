package seedu.exercise.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.exercise.commons.exceptions.DataConversionException;

/**
 * Represents a storage for {@code PropertyBook}
 */
public interface PropertyBookStorage {

    /**
     * Returns the file path of the PropertyBook data file.
     */
    Path getPropertyBookFilePath();

    /**
     * Reads the data of the {@code PropertyBook} in storage.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    void readPropertyBook() throws DataConversionException, IOException;

    /**
     * @see #readPropertyBook()
     */
    void readPropertyBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the {@code PropertyBook} to the storage.
     *
     * @throws IOException if there was any problem writing to the file.
     */
    void savePropertyBook() throws IOException;

    /**
     * @see #savePropertyBook()
     */
    void savePropertyBook(Path filePath) throws IOException;
}
