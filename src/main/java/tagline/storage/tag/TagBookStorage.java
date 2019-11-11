//@@author stevenwjy
package tagline.storage.tag;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tagline.commons.exceptions.DataConversionException;
import tagline.model.tag.ReadOnlyTagBook;
import tagline.model.tag.UniqueTagList;

/**
 * Represents a storage for {@link UniqueTagList}.
 */
public interface TagBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTagBookFilePath();

    /**
     * Returns TagBook data as a {@link ReadOnlyTagBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTagBook> readTagBook() throws DataConversionException, IOException;

    /**
     * @see #readTagBook()
     */
    Optional<ReadOnlyTagBook> readTagBook(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTagBook} to the storage.
     *
     * @param tagList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTagBook(ReadOnlyTagBook tagList) throws IOException;

    /**
     * @see #saveTagBook(ReadOnlyTagBook)
     */
    void saveTagBook(ReadOnlyTagBook addressBook, Path filePath) throws IOException;
}
