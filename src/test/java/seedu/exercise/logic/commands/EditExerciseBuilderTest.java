package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.CommonTestData.DESC_AEROBICS;
import static seedu.exercise.testutil.CommonTestData.DESC_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_CALORIES_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_DATE_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_MUSCLE_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_NAME_BASKETBALL;
import static seedu.exercise.testutil.CommonTestData.VALID_QUANTITY_BASKETBALL;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.builder.EditExerciseBuilder;
import seedu.exercise.testutil.builder.EditExerciseDescriptorBuilder;

public class EditExerciseBuilderTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditExerciseBuilder descriptorWithSameValues =
            new EditExerciseBuilder(DESC_AEROBICS);
        assertTrue(DESC_AEROBICS.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_AEROBICS.equals(DESC_AEROBICS));

        // null -> returns false
        assertFalse(DESC_AEROBICS.equals(null));

        // different types -> returns false
        assertFalse(DESC_AEROBICS.equals(5));

        // different values -> returns false
        assertFalse(DESC_AEROBICS.equals(DESC_BASKETBALL));

        // different name -> returns false
        EditExerciseBuilder editedAerobics =
            new EditExerciseDescriptorBuilder(DESC_AEROBICS).withName(VALID_NAME_BASKETBALL).build();
        assertFalse(DESC_AEROBICS.equals(editedAerobics));

        // different calories -> returns false
        editedAerobics = new EditExerciseDescriptorBuilder(DESC_AEROBICS)
            .withCalories(VALID_CALORIES_BASKETBALL).build();
        assertFalse(DESC_AEROBICS.equals(editedAerobics));

        // different date -> returns false
        editedAerobics = new EditExerciseDescriptorBuilder(DESC_AEROBICS)
            .withDate(VALID_DATE_BASKETBALL).build();
        assertFalse(DESC_AEROBICS.equals(editedAerobics));

        // different quantity -> returns false
        editedAerobics = new EditExerciseDescriptorBuilder(DESC_AEROBICS)
            .withQuantity(VALID_QUANTITY_BASKETBALL).build();
        assertFalse(DESC_AEROBICS.equals(editedAerobics));

        // different muscles -> returns false
        editedAerobics = new EditExerciseDescriptorBuilder(DESC_AEROBICS)
            .withMuscles(VALID_MUSCLE_BASKETBALL).build();
        assertFalse(DESC_AEROBICS.equals(editedAerobics));
    }
}
