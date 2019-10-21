package seedu.address.storage.quiz;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.quiz.ReadOnlyQuizzes;
import seedu.address.model.quiz.SavedQuizzes;

/**
 * Represents a storage for {@link SavedQuizzes}.
 */
public interface QuizStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getSavedQuizzesFilePath();

    /**
     * Returns SavedQuestions data as a {@link ReadOnlyQuizzes}. Returns {@code Optional.empty()}
     * if storage file is not found.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyQuizzes> readQuizzes() throws DataConversionException, IOException;

    /**
     * @see #getSavedQuizzesFilePath()
     */
    Optional<ReadOnlyQuizzes> readQuizzes(Path filePath)
            throws DataConversionException, IOException;

    /**
     * Saves the given {@link ReadOnlyQuizzes} to the storage.
     *
     * @param quizzes cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveQuizzes(ReadOnlyQuizzes quizzes) throws IOException;

    /**
     * @see #saveQuizzes(ReadOnlyQuizzes)
     */
    void saveQuizzes(ReadOnlyQuizzes quizzes, Path filePath) throws IOException;

}
