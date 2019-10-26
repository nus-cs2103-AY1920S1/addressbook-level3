package com.dukeacademy.storage.question;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.commons.util.FileUtil;
import com.dukeacademy.commons.util.JsonUtil;

import com.dukeacademy.model.question.QuestionBank;

/**
 * A class to access QuestionBank data stored as a json file on the hard disk.
 */
public class JsonQuestionBankStorage implements QuestionBankStorage {
    private Path filePath;

    public JsonQuestionBankStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getQuestionBankFilePath() {
        return filePath;
    }

    @Override
    public Optional<QuestionBank> readQuestionBank() throws DataConversionException {
        return readQuestionBank(filePath);
    }

    /**
     * Similar to {@link #readQuestionBank()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<QuestionBank> readQuestionBank(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableStandardQuestionBank> jsonQuestionBank = JsonUtil.readJsonFile(
                filePath, JsonSerializableStandardQuestionBank.class);
        if (!jsonQuestionBank.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonQuestionBank.get().toModelType());
        } catch (IllegalValueException e) {
            throw new DataConversionException(e);
        }
    }

    @Override
    public void saveQuestionBank(QuestionBank questionBank) throws IOException {
        saveQuestionBank(questionBank, filePath);
    }

    /**
     * Similar to {@link #saveQuestionBank(QuestionBank)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveQuestionBank(QuestionBank questionBank, Path filePath) throws IOException {
        requireNonNull(questionBank);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableStandardQuestionBank(questionBank), filePath);
    }
}
