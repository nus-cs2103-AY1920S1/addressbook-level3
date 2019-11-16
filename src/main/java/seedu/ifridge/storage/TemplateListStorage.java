package seedu.ifridge.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.ifridge.commons.exceptions.DataConversionException;
import seedu.ifridge.model.ReadOnlyTemplateList;
import seedu.ifridge.model.TemplateList;

/**
 * Represents a storage for {@link TemplateList}.
 */
public interface TemplateListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTemplateListFilePath();

    /**
     * Returns TemplateList data as a {@link ReadOnlyTemplateList}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTemplateList> readTemplateList() throws DataConversionException, IOException;

    /**
     * @see #getTemplateListFilePath()
     */
    Optional<ReadOnlyTemplateList> readTemplateList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTemplateList} to the storage.
     * @param templateList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTemplateList(ReadOnlyTemplateList templateList) throws IOException;

    /**
     * @see #saveTemplateList(ReadOnlyTemplateList)
     */
    void saveTemplateList(ReadOnlyTemplateList templateList, Path filePath) throws IOException;

}
