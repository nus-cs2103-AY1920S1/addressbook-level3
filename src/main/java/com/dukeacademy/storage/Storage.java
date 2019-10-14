package com.dukeacademy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.dukeacademy.commons.exceptions.DataConversionException;

import com.dukeacademy.model.ReadOnlyQuestionBank;
import com.dukeacademy.model.ReadOnlyUserPrefs;
import com.dukeacademy.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends QuestionBankStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<ReadOnlyQuestionBank> readQuestionBank() throws DataConversionException, IOException;

    @Override
    void saveQuestionBank(ReadOnlyQuestionBank questionBank) throws IOException;

}
