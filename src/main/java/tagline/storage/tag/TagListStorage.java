package tagline.storage.tag;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import tagline.commons.exceptions.DataConversionException;
import tagline.model.tag.ReadOnlyTagList;

/**
 * Represents a storage for {@link tagline.model.tag.TagList}.
 */
public interface TagListStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getTagListFilePath();

    /**
     * Returns TagList data as a {@link ReadOnlyTagList}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyTagList> readTagList() throws DataConversionException, IOException;

    /**
     * @see #readTagList()
     */
    Optional<ReadOnlyTagList> readTagList(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyTagList} to the storage.
     *
     * @param tagList cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveTagList(ReadOnlyTagList tagList) throws IOException;

    /**
     * @see #saveTagList(ReadOnlyTagList)
     */
    void saveTagList(ReadOnlyTagList addressBook, Path filePath) throws IOException;
}
