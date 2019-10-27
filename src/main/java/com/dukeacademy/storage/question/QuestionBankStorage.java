package com.dukeacademy.storage.question;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.model.question.QuestionBank;
import com.dukeacademy.model.question.StandardQuestionBank;

/**
 * Represents a storage for {@link StandardQuestionBank}.
 */
public interface QuestionBankStorage {

    /**
     * Returns the file path of the data file.
     *
     * @return the question bank file path
     */
    Path getQuestionBankFilePath();

    /**
     * Returns QuestionBank data as a {@link QuestionBank}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @return the optional
     * @throws DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<QuestionBank> readQuestionBank() throws DataConversionException, IOException;

    /**
     * Read question bank optional.
     *
     * @param filePath the file path
     * @return the optional
     * @throws DataConversionException the data conversion exception
     * @throws IOException             the io exception
     * @see #getQuestionBankFilePath() #getQuestionBankFilePath()
     */
    Optional<QuestionBank> readQuestionBank(Path filePath) throws DataConversionException, IOException;

    /**
     * Saves the given {@link QuestionBank} to the storage.
     *
     * @param questionBank cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveQuestionBank(QuestionBank questionBank) throws IOException;

    /**
     * Save question bank.
     *
     * @param questionBank the question bank
     * @param filePath     the file path
     * @throws IOException the io exception
     * @see #saveQuestionBank(QuestionBank) #saveQuestionBank(QuestionBank)
     */
    void saveQuestionBank(QuestionBank questionBank, Path filePath) throws IOException;

}
