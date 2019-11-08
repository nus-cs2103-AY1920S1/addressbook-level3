package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyStudyBuddyPro;
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
     * Returns StudyBuddyPro data as a {@link ReadOnlyStudyBuddyPro}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudyBuddyPro> readStudyBuddyPro() throws DataConversionException, IOException;

    /**
     * Reads data from filepaths provided.
     * @param flashcardFilePath Flashcard JSON file Path
     * @param noteFilePath Notes JSON file Path
     * @param cheatsheetFilePath Cheatsheet JSON file Path
     * @return Returns {@code Optional.empty()} if storage file is not found.
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyStudyBuddyPro> readStudyBuddyPro(Path flashcardFilePath, Path noteFilePath,
                                                      Path cheatsheetFilePath)
            throws DataConversionException, IOException;

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

