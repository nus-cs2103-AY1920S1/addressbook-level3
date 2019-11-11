package seedu.address.storage.question;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.question.ReadOnlyQuestions;

/**
 * A class to access SavedQuestions data stored as a json file on the hard disk.
 */
public class JsonQuestionStorage implements QuestionStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonQuestionStorage.class);

    private Path filePath;

    public JsonQuestionStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSavedQuestionsFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyQuestions> readQuestions() throws DataConversionException {
        return readQuestions(filePath);
    }

    /**
     * Similar to {@link #readQuestions()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyQuestions> readQuestions(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableQuestions> jsonQuestions = JsonUtil.readJsonFile(
            filePath, JsonSerializableQuestions.class);
        if (!jsonQuestions.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonQuestions.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveQuestions(ReadOnlyQuestions questions) throws IOException {
        saveQuestions(questions, filePath);
    }

    /**
     * Similar to {@link #saveQuestions(ReadOnlyQuestions)}.
     *
     * @param filePath location of the data.
     */
    public void saveQuestions(ReadOnlyQuestions questions, Path filePath) throws IOException {
        requireNonNull(questions);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableQuestions(questions), filePath);
    }

}
