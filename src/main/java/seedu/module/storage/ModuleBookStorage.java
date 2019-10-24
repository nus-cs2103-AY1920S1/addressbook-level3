package seedu.module.storage;

import java.io.IOException;
import java.nio.file.Path;

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
     * Returns an empty ModuleBook if storage file is not found.
     */
    ReadOnlyModuleBook readModuleBook();

    /**
     * @see #readModuleBook()
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
