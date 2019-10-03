package seedu.exercise.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.exceptions.IllegalValueException;
import seedu.exercise.commons.util.JsonUtil;
import seedu.exercise.model.ExerciseBook;
import seedu.exercise.testutil.TypicalExercises;

public class JsonSerializableExerciseBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableExerciseBookTest");
    private static final Path TYPICAL_EXERCISES_FILE = TEST_DATA_FOLDER.resolve("typicalExerciseBook.json");
    private static final Path INVALID_EXERCISE_FILE = TEST_DATA_FOLDER.resolve("invalidExerciseBook.json");
    private static final Path DUPLICATE_EXERCISE_FILE = TEST_DATA_FOLDER.resolve("duplicateExerciseBook.json");

    @Test
    public void toModelType_typicalExercisesFile_success() throws Exception {
        JsonSerializableExerciseBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_EXERCISES_FILE,
                JsonSerializableExerciseBook.class).get();
        ExerciseBook exerciseBookFromFile = dataFromFile.toModelType();
        ExerciseBook typicalExerciseBook = TypicalExercises.getTypicalExerciseBook();
        assertEquals(exerciseBookFromFile, typicalExerciseBook);
    }

    @Test
    public void toModelType_invalidExerciseFile_throwsIllegalValueException() throws Exception {
        JsonSerializableExerciseBook dataFromFile = JsonUtil.readJsonFile(INVALID_EXERCISE_FILE,
                JsonSerializableExerciseBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateExercise_throwsIllegalValueException() throws Exception {
        JsonSerializableExerciseBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_EXERCISE_FILE,
                JsonSerializableExerciseBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableExerciseBook.MESSAGE_DUPLICATE_EXERCISE,
                dataFromFile::toModelType);
    }

}
