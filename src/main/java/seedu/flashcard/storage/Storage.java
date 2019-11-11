package seedu.flashcard.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.flashcard.commons.exceptions.DataConversionException;
import seedu.flashcard.model.ReadOnlyFlashcardList;
import seedu.flashcard.model.ReadOnlyUserPrefs;
import seedu.flashcard.model.UserPrefs;

/**
 * The general interface of all storage usage.
 * This is the window for storage package to communicate to the outside.
 */
public interface Storage extends FlashcardStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getFlashcardListFilePath();

    @Override
    Optional<ReadOnlyFlashcardList> readFlashcardList() throws DataConversionException, IOException;

    @Override
    Optional<ReadOnlyFlashcardList> readFlashcardList(Path filePath) throws DataConversionException, IOException;
}
