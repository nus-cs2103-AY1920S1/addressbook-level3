package seedu.address.logic.commands.exercise;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.exercise.ReadOnlyWorkoutPlanner;
import seedu.address.model.exercise.WorkoutPlanner;
import seedu.address.model.exercise.components.Exercise;
import seedu.address.testutil.exercise.ExerciseBuilder;

public class AddExerciseCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddExerciseCommand(null));
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

        assertThrows(CommandException.class, AddExerciseCommand
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
        public ReadOnlyWorkoutPlanner getWorkoutPlanner() {
            return new WorkoutPlanner();
        }
    }

}
