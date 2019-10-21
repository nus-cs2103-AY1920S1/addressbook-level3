package seedu.address.storage.quiz;

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
import seedu.address.model.quiz.ReadOnlyQuizzes;

/**
 * A class to access SavedQuizzes data stored as a json file on the hard disk.
 */
public class JsonQuizStorage implements QuizStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonQuizStorage.class);

    private Path filePath;

    public JsonQuizStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getSavedQuizzesFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyQuizzes> readQuizzes() throws DataConversionException {
        return readQuizzes(filePath);
    }

    /**
     * Similar to {@link #readQuizzes()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyQuizzes> readQuizzes(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableQuizzes> jsonQuizzes = JsonUtil.readJsonFile(
            filePath, JsonSerializableQuizzes.class);
        if (!jsonQuizzes.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonQuizzes.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }
    }

    @Override
    public void saveQuizzes(ReadOnlyQuizzes quizzes) throws IOException {
        saveQuizzes(quizzes, filePath);
    }

    /**
     * Similar to {@link #saveQuizzes(ReadOnlyQuizzes)}.
     *
     * @param filePath location of the data.
     */
    @Override
    public void saveQuizzes(ReadOnlyQuizzes quizzes, Path filePath) throws IOException {
        requireNonNull(quizzes);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableQuizzes(quizzes), filePath);
    }

}
