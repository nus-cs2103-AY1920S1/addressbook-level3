package seedu.exercise.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.model.property.PropertyBook;

/**
 * Represents a storage for {@code PropertyBook}
 */
public interface PropertyBookStorage {

    /**
     * Returns the file path of the PropertyBook data file.
     */
    Path getPropertyBookFilePath();

    /**
     * Returns PropertyBook data as a {@code PropertyBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<PropertyBook> readPropertyBook() throws DataConversionException, IOException;

    /**
     * @see #readPropertyBook()
     */
    Optional<PropertyBook> readPropertyBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@code PropertyBook} to the storage.
     *
     * @throws IOException if there was any problem writing to the file.
     */
    void savePropertyBook(PropertyBook propertyBook) throws IOException;

    /**
     * @see #savePropertyBook(PropertyBook)
     */
    void savePropertyBook(PropertyBook propertyBook, Path filePath) throws IOException;
}
