package seedu.flashcard.storage;

import seedu.flashcard.commons.exceptions.DataConversionException;
import seedu.flashcard.model.ReadOnlyFlashcardList;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link seedu.flashcard.model.FlashcardList}
 */
public interface FlashcardStorage {

    /**
     * get the file path of the data file.
     */
    Path getFlashcardListFilePath();

    /**
     * Returns flashcard list data as a {@code ReadOnlyFlashcardList}.
     * @return the flashcard list been read.
     * @throws DataConversionException if the data in storage is not in expected format.
     * @throws IOException is there is any problem while reading from the storage.
     */
    Optional<ReadOnlyFlashcardList> readFlashcardList() throws DataConversionException, IOException;

    /**
     * Same as {@code readFlashcardList()}, but get the flashcard list from a file.
     */
    Optional<ReadOnlyFlashcardList> readFlashcardList(Path filePath) throws DataConversionException, IOException;

    /**
     * saves the given flashcard list {@code flashcardList} into storage.
     * @param flashcardList the flashcard list to be saved.
     * @throws IOException if there was any problem in the saving process.
     */
    void saveFlashcardList(ReadOnlyFlashcardList flashcardList) throws IOException;

    /**
     * Same as {@code saveFlashcardList(ReadOnlyFlashcardList flashcardList)}, but specifies which file to save to.
     */
    void saveFlashcardList(ReadOnlyFlashcardList flashcardList, Path filePath) throws IOException;
}
