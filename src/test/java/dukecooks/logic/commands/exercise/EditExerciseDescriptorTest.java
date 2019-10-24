package dukecooks.logic.commands.exercise;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.testutil.exercise.EditExerciseDescriptorBuilder;

public class EditExerciseDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditExerciseCommand.EditExerciseDescriptor descriptorWithSameValues = new EditExerciseCommand
                .EditExerciseDescriptor(CommandTestUtil.DESC_PUSHUP);
        Assertions.assertTrue(CommandTestUtil.DESC_PUSHUP.equals(descriptorWithSameValues));

        // same object -> returns true
        Assertions.assertTrue(CommandTestUtil.DESC_PUSHUP.equals(CommandTestUtil.DESC_PUSHUP));

        // null -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_PUSHUP.equals(null));

        // different types -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_PUSHUP.equals(5));

        // different values -> returns false
        Assertions.assertFalse(CommandTestUtil.DESC_PUSHUP.equals(CommandTestUtil.DESC_SITUP));

        // different name -> returns false
        EditExerciseCommand.EditExerciseDescriptor editedAmy = new EditExerciseDescriptorBuilder(CommandTestUtil
                .DESC_PUSHUP)
                .withName(CommandTestUtil.VALID_NAME_SITUP).build();
        Assertions.assertFalse(CommandTestUtil.DESC_PUSHUP.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditExerciseDescriptorBuilder(CommandTestUtil.DESC_PUSHUP)
                .withDetails(null, null, null, null, null,
                        CommandTestUtil.VALID_REPS_SIXTY)
                .build();
        Assertions.assertFalse(CommandTestUtil.DESC_PUSHUP.equals(editedAmy));
    }
}
