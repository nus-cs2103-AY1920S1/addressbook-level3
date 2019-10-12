package com.dukeacademy.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.dukeacademy.commons.core.LogsCenter;
import com.dukeacademy.commons.exceptions.DataConversionException;
import com.dukeacademy.commons.exceptions.IllegalValueException;
import com.dukeacademy.commons.util.FileUtil;
import com.dukeacademy.commons.util.JsonUtil;

import com.dukeacademy.model.ReadOnlyQuestionBank;

/**
 * A class to access QuestionBank data stored as a json file on the hard disk.
 */
public class JsonQuestionBankStorage implements QuestionBankStorage {

    private static final Logger logger = LogsCenter.getLogger(
        JsonQuestionBankStorage.class);

    private Path filePath;

    public JsonQuestionBankStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyQuestionBank> readQuestionBank() throws DataConversionException {
        return readQuestionBank(filePath);
    }

    /**
     * Similar to {@link #readQuestionBank()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyQuestionBank> readQuestionBank(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableQuestionBank> jsonAddressBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableQuestionBank.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveQuestionBank(ReadOnlyQuestionBank addressBook) throws IOException {
        saveQuestionBank(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveQuestionBank(ReadOnlyQuestionBank)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveQuestionBank(ReadOnlyQuestionBank addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableQuestionBank(addressBook), filePath);
    }
}
