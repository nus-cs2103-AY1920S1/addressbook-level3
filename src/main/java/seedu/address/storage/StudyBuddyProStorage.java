package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.CheatSheetDataConversionException;
import seedu.address.commons.exceptions.FlashcardDataConversionException;
import seedu.address.commons.exceptions.NoteDataConversionException;
import seedu.address.model.ReadOnlyStudyBuddyPro;
import seedu.address.model.ReadOnlyStudyBuddyProCheatSheets;
import seedu.address.model.ReadOnlyStudyBuddyProFlashcards;
import seedu.address.model.ReadOnlyStudyBuddyProNotes;
import seedu.address.model.StudyBuddyPro;

/**
 * Represents a storage for {@link StudyBuddyPro}.
 */

public interface StudyBuddyProStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getCheatSheetFilePath();
    Path getFlashcardFilePath();
    Path getNoteFilePath();

    /**
     * Returns StudyBuddyPro flashcard data as a {@link ReadOnlyStudyBuddyProFlashcards}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws FlashcardDataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudyBuddyProFlashcards> readStudyBuddyProFlashcards()
            throws FlashcardDataConversionException, IOException;

    /**
     * Reads data from filepath provided.
     * @param flashcardFilePath Flashcard JSON file Path
     * @return Returns {@code Optional.empty()} if storage file is not found.
     * @throws FlashcardDataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudyBuddyProFlashcards> readStudyBuddyProFlashcards(Path flashcardFilePath)
            throws FlashcardDataConversionException, IOException;

    /**
     * Returns StudyBuddyPro note data as a {@link ReadOnlyStudyBuddyProNotes}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws NoteDataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudyBuddyProNotes> readStudyBuddyProNotes()
            throws NoteDataConversionException, IOException;

    /**
     * Reads data from filepath provided.
     * @param notesFilePath Flashcard JSON file Path
     * @return Returns {@code Optional.empty()} if storage file is not found.
     * @throws NoteDataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudyBuddyProNotes> readStudyBuddyProNotes(Path notesFilePath)
            throws NoteDataConversionException, IOException;

    /**
     * Returns StudyBuddyPro cheatsheet data as a {@link ReadOnlyStudyBuddyProCheatSheets}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws CheatSheetDataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudyBuddyProCheatSheets> readStudyBuddyProCheatSheets()
            throws CheatSheetDataConversionException, IOException;

    /**
     * Reads data from filepath provided.
     * @param cheatSheetsFilePath Flashcard JSON file Path
     * @return Returns {@code Optional.empty()} if storage file is not found.
     * @throws CheatSheetDataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudyBuddyProCheatSheets> readStudyBuddyProCheatSheets(Path cheatSheetsFilePath)
            throws CheatSheetDataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyStudyBuddyPro} to the storage.
     * @param studyBuddyPro cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStudyBuddyPro(ReadOnlyStudyBuddyPro studyBuddyPro) throws IOException;

    /**
     * Saves the given {@link ReadOnlyStudyBuddyPro} to the storage from filepaths provided.
     * @param studyBuddyPro cannot be null.
     * @param flashcardFilePath cannot be null.
     * @param noteFilePath cannot be null.
     * @param cheatsheetFilePath cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveStudyBuddyPro(ReadOnlyStudyBuddyPro studyBuddyPro, Path flashcardFilePath, Path noteFilePath,
                           Path cheatsheetFilePath) throws IOException;
}

