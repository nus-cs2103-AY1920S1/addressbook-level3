package seedu.address.diaryfeature.storage;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.diaryfeature.model.DiaryBook;


/**
 * Represents a storage for {@link DiaryBook}.
 */

public interface DiaryBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getDiaryBookFilePath();

    void saveDiaryBook(DiaryBook diaryBook) throws IOException;

    /**
     *
     */
    void saveDiaryBook(DiaryBook diaryBook, Path filePath) throws IOException;

}
