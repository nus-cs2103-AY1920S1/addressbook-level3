package seedu.exercise.storage.bookstorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.exercise.commons.exceptions.DataConversionException;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Resource;

/**
 * API of a ResourceBookStorage.
 */
public interface ResourceBookStorage<T extends Resource> {

    /**
     * Retrieves the file path of the ResourceBook data file.
     */
    Path getResourceBookFilePath();

    /**
     * Reads the resource data from storage.
     * Returns {@code Optional.Empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not found.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyResourceBook<T>> readResourceBook() throws DataConversionException, IOException;

    /**
     * Works similarly to {@link ResourceBookStorage#readResourceBook()}.
     * However, this reads the data in the given {@code Path}.
     */
    Optional<ReadOnlyResourceBook<T>> readResourceBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@code ReadOnlyResourceBook<T>} to the storage.
     *
     * @param resourceBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveResourceBook(ReadOnlyResourceBook<T> resourceBook) throws IOException;

    /**
     * Works similarly to {@link ResourceBookStorage#saveResourceBook(ReadOnlyResourceBook)}.
     * However, this stores the data in the given {@code Path}.
     */
    void saveResourceBook(ReadOnlyResourceBook<T> resourceBook, Path filePath) throws IOException;
}
