package seedu.address.storage.question;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.question.ReadOnlyQuestions;
import seedu.address.model.question.SavedQuestions;

/**
 * Represents a storage for {@link SavedQuestions}.
 */
public interface QuestionStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSavedQuestionsFilePath();

    /**
     * Returns SavedQuestions data as a {@link ReadOnlyQuestions}. Returns {@code Optional.empty()}
     * if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyQuestions> readQuestions() throws DataConversionException, IOException;

    /**
     * @see #getSavedQuestionsFilePath()
     */
    Optional<ReadOnlyQuestions> readQuestions(Path filePath)
        throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyQuestions} to the storage.
     *
     * @param questions cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveQuestions(ReadOnlyQuestions questions) throws IOException;

    /**
     * @see #saveQuestions(ReadOnlyQuestions)
     */
    void saveQuestions(ReadOnlyQuestions questions, Path filePath) throws IOException;

}
