package seedu.module.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.module.commons.exceptions.DataConversionException;
import seedu.module.model.ModuleBook;
import seedu.module.model.ReadOnlyModuleBook;

/**
 * Represents a storage for {@link ModuleBook}.
 */
public interface ModuleBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getModuleBookFilePath();

    /**
     * Returns ModuleBook data as a {@link ModuleBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    ReadOnlyModuleBook readModuleBook();

    /**
     * @see #getModuleBookFilePath()
     */
    ReadOnlyModuleBook readModuleBook(Path filePath);

    /**
     * Saves the given {@link ReadOnlyModuleBook} to the storage.
     *
     * @param moduleBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveModuleBook(ReadOnlyModuleBook moduleBook) throws IOException;

    /**
     * @see #saveModuleBook(ReadOnlyModuleBook)
     */
    void saveModuleBook(ReadOnlyModuleBook moduleBook, Path filePath) throws IOException;

}
