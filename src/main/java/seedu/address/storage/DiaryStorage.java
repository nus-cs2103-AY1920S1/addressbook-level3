package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.DiaryRecords;
import seedu.address.model.ReadOnlyDiary;

/**
 * Represents a storage for {@link DiaryRecords}.
 */
public interface DiaryStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDiaryFilePath();

    /**
     * Returns DiaryRecords data as a {@link ReadOnlyDiary}.
     *   Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyDiary> readDiary() throws DataConversionException, IOException;

    /**
     * @see #getDiaryFilePath()
     */
    Optional<ReadOnlyDiary> readDiary(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyDiary} to the storage.
     * @param diary cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveDiary(ReadOnlyDiary diary) throws IOException;

    /**
     * @see #saveDiary(ReadOnlyDiary)
     */
    void saveDiary(ReadOnlyDiary diaries, Path filePath) throws IOException;

}
