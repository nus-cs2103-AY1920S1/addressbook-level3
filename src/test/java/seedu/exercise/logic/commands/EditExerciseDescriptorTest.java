package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.DESC_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.DESC_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_CALORIES_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_DATE_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_MUSCLE_AEROBICS;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_NAME_BASKETBALL;
import static seedu.exercise.logic.commands.CommandTestUtil.VALID_QUANTITY_BASKETBALL;

import org.junit.jupiter.api.Test;

import seedu.exercise.testutil.EditExerciseDescriptorBuilder;

public class EditExerciseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditCommand.EditExerciseDescriptor descriptorWithSameValues =
            new EditCommand.EditExerciseDescriptor(DESC_AEROBICS);
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
        EditCommand.EditExerciseDescriptor editedAerobics =
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
            .withMuscles(VALID_MUSCLE_AEROBICS).build();
        assertFalse(DESC_AEROBICS.equals(editedAerobics));
    }
}
