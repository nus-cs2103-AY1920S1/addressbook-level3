package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.CheatSheetDataConversionException;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.FlashcardDataConversionException;
import seedu.address.commons.exceptions.NoteDataConversionException;
import seedu.address.model.ReadOnlyStudyBuddyPro;
import seedu.address.model.ReadOnlyStudyBuddyProCheatSheets;
import seedu.address.model.ReadOnlyStudyBuddyProFlashcards;
import seedu.address.model.ReadOnlyStudyBuddyProNotes;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends StudyBuddyProStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getCheatSheetFilePath();

    @Override
    Path getFlashcardFilePath();

    @Override
    Path getNoteFilePath();

    @Override
    Optional<ReadOnlyStudyBuddyProFlashcards> readStudyBuddyProFlashcards()
            throws FlashcardDataConversionException, IOException;

    @Override
    Optional<ReadOnlyStudyBuddyProNotes> readStudyBuddyProNotes()
            throws NoteDataConversionException, IOException;

    @Override
    Optional<ReadOnlyStudyBuddyProCheatSheets> readStudyBuddyProCheatSheets()
            throws CheatSheetDataConversionException, IOException;

    @Override
    void saveStudyBuddyPro(ReadOnlyStudyBuddyPro studyBuddyPro) throws IOException;
}
