package dukecooks.logic.commands.exercise;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.ModelStub;
import dukecooks.model.workout.exercise.ReadOnlyExerciseCatalogue;
import dukecooks.model.workout.exercise.ExerciseCatalogue;
import dukecooks.model.workout.exercise.components.Exercise;
import dukecooks.testutil.Assert;
import dukecooks.testutil.exercise.ExerciseBuilder;

public class AddExerciseCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddExerciseCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Exercise validExercise = new ExerciseBuilder().build();

        CommandResult commandResult = new AddExerciseCommand(validExercise).execute(modelStub);

        assertEquals(String.format(AddExerciseCommand
                .MESSAGE_SUCCESS, validExercise), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validExercise), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Exercise validExercise = new ExerciseBuilder().build();
        AddExerciseCommand addExerciseCommand = new AddExerciseCommand(validExercise);
        ModelStub modelStub = new ModelStubWithPerson(validExercise);

        Assert.assertThrows(CommandException.class, AddExerciseCommand
                .MESSAGE_DUPLICATE_PERSON, () -> addExerciseCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Exercise alice = new ExerciseBuilder().withName("Alice").build();
        Exercise bob = new ExerciseBuilder().withName("Bob").build();
        AddExerciseCommand addAliceCommand = new AddExerciseCommand(alice);
        AddExerciseCommand addBobCommand = new AddExerciseCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddExerciseCommand addAliceCommandCopy = new AddExerciseCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }


    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Exercise exercise;

        ModelStubWithPerson(Exercise exercise) {
            requireNonNull(exercise);
            this.exercise = exercise;
        }

        @Override
        public boolean hasExercise(Exercise exercise) {
            requireNonNull(exercise);
            return this.exercise.isSameExercise(exercise);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Exercise> personsAdded = new ArrayList<>();

        @Override
        public boolean hasExercise(Exercise exercise) {
            requireNonNull(exercise);
            return personsAdded.stream().anyMatch(exercise::isSameExercise);
        }

        @Override
        public void addExercise(Exercise exercise) {
            requireNonNull(exercise);
            personsAdded.add(exercise);
        }

        @Override
        public ReadOnlyExerciseCatalogue getExerciseCatalogue() {
            return new ExerciseCatalogue();
        }
    }

}
