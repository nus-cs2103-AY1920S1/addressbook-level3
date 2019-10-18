package seedu.address.logic.commands.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_PUSHUP;
import static seedu.address.logic.commands.CommandTestUtil.DESC_SITUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_SITUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REPS_SIXTY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exercise.EditExerciseCommand.EditExerciseDescriptor;
import seedu.address.testutil.exercise.EditExerciseDescriptorBuilder;

public class EditExerciseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditExerciseDescriptor descriptorWithSameValues = new EditExerciseDescriptor(DESC_PUSHUP);
        assertTrue(DESC_PUSHUP.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(DESC_PUSHUP.equals(DESC_PUSHUP));

        // null -> returns false
        assertFalse(DESC_PUSHUP.equals(null));

        // different types -> returns false
        assertFalse(DESC_PUSHUP.equals(5));

        // different values -> returns false
        assertFalse(DESC_PUSHUP.equals(DESC_SITUP));

        // different name -> returns false
        EditExerciseCommand.EditExerciseDescriptor editedAmy = new EditExerciseDescriptorBuilder(DESC_PUSHUP)
                .withName(VALID_NAME_SITUP).build();
        assertFalse(DESC_PUSHUP.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditExerciseDescriptorBuilder(DESC_PUSHUP)
                .withDetails(null, null, null, null, null, VALID_REPS_SIXTY)
                .build();
        assertFalse(DESC_PUSHUP.equals(editedAmy));
    }
}
