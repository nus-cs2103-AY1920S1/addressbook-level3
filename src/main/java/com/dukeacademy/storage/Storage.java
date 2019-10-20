package com.dukeacademy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.dukeacademy.commons.exceptions.DataConversionException;

import com.dukeacademy.model.question.QuestionBank;
import com.dukeacademy.model.prefs.ReadOnlyUserPrefs;
import com.dukeacademy.model.prefs.UserPrefs;
import com.dukeacademy.storage.prefs.UserPrefsStorage;
import com.dukeacademy.storage.question.QuestionBankStorage;

/**
 * API of the Storage component
 */
public interface Storage extends QuestionBankStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getQuestionBankFilePath();

    @Override
    Optional<QuestionBank> readQuestionBank() throws DataConversionException, IOException;

    @Override
    void saveQuestionBank(QuestionBank questionBank) throws IOException;

}
