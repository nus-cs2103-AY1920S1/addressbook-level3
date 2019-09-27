package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedExercise.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalExercises.SWIM;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.exercise.Calories;
import seedu.address.model.exercise.Date;
import seedu.address.model.exercise.Name;
import seedu.address.model.exercise.Quantity;

public class JsonAdaptedExerciseTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DATE = "32/13/2019";
    private static final String INVALID_QUANTITY = " ";
    private static final String INVALID_CALORIES = "example.com";
    private static final String INVALID_MUSCLE = "#friend";

    private static final String VALID_NAME = SWIM.getName().toString();
    private static final String VALID_DATE = SWIM.getDate().toString();
    private static final String VALID_CALORIES = SWIM.getCalories().toString();
    private static final String VALID_QUANTITY = SWIM.getQuantity().toString();
    private static final List<JsonAdaptedMuscle> VALID_MUSCLES = SWIM.getMuscles().stream()
        .map(JsonAdaptedMuscle::new)
        .collect(Collectors.toList());

    @Test
    public void toModelType_validExerciseDetails_returnsExercise() throws Exception {
        JsonAdaptedExercise person = new JsonAdaptedExercise(SWIM);
        assertEquals(SWIM, person.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedExercise exercise =
            new JsonAdaptedExercise(INVALID_NAME, VALID_DATE, VALID_CALORIES, VALID_QUANTITY, VALID_MUSCLES);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, exercise::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedExercise exercise = new JsonAdaptedExercise(null, VALID_DATE, VALID_CALORIES,
            VALID_QUANTITY, VALID_MUSCLES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, exercise::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedExercise exercise =
            new JsonAdaptedExercise(VALID_NAME, INVALID_DATE, VALID_CALORIES, VALID_QUANTITY, VALID_MUSCLES);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, exercise::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedExercise exercise = new JsonAdaptedExercise(VALID_NAME, null, VALID_CALORIES,
            VALID_QUANTITY, VALID_MUSCLES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, exercise::toModelType);
    }

    @Test
    public void toModelType_invalidCalories_throwsIllegalValueException() {
        JsonAdaptedExercise exercise =
            new JsonAdaptedExercise(VALID_NAME, VALID_DATE, INVALID_CALORIES, VALID_QUANTITY, VALID_MUSCLES);
        String expectedMessage = Calories.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, exercise::toModelType);
    }

    @Test
    public void toModelType_nullCalories_throwsIllegalValueException() {
        JsonAdaptedExercise exercise = new JsonAdaptedExercise(VALID_NAME, VALID_DATE, null, VALID_QUANTITY,
            VALID_MUSCLES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Calories.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, exercise::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        JsonAdaptedExercise exercise =
            new JsonAdaptedExercise(VALID_NAME, VALID_DATE, VALID_CALORIES, INVALID_QUANTITY, VALID_MUSCLES);
        String expectedMessage = Quantity.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, exercise::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        JsonAdaptedExercise exercise = new JsonAdaptedExercise(VALID_NAME, VALID_DATE, VALID_CALORIES, null,
            VALID_MUSCLES);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, exercise::toModelType);
    }

    @Test
    public void toModelType_invalidMuscles_throwsIllegalValueException() {
        List<JsonAdaptedMuscle> invalidMuscles = new ArrayList<>(VALID_MUSCLES);
        invalidMuscles.add(new JsonAdaptedMuscle(INVALID_MUSCLE));
        JsonAdaptedExercise exercise =
            new JsonAdaptedExercise(VALID_NAME, VALID_DATE, VALID_CALORIES, VALID_QUANTITY, invalidMuscles);
        assertThrows(IllegalValueException.class, exercise::toModelType);
    }

}
