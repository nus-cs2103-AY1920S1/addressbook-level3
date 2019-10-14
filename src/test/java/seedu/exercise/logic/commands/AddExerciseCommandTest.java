package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.exercise.commons.core.GuiSettings;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.parser.Prefix;
import seedu.exercise.model.ExerciseBook;
import seedu.exercise.model.Model;
import seedu.exercise.model.ReadOnlyExerciseBook;
import seedu.exercise.model.ReadOnlyRegimeBook;
import seedu.exercise.model.ReadOnlyUserPrefs;
import seedu.exercise.model.exercise.CustomProperty;
import seedu.exercise.model.exercise.Exercise;
import seedu.exercise.model.exercise.PropertyManager;
import seedu.exercise.model.regime.Regime;
import seedu.exercise.testutil.ExerciseBuilder;

public class AddExerciseCommandTest {

    @Test
    public void constructor_nullExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddExerciseCommand(null));
    }

    @Test
    public void execute_exerciseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingExerciseAdded modelStub = new ModelStubAcceptingExerciseAdded();
        Exercise validPerson = new ExerciseBuilder().build();

        CommandResult commandResult = new AddExerciseCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddExerciseCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.exercisesAdded);
    }

    @Test
    public void execute_duplicateExercise_throwsCommandException() {
        Exercise validExercise = new ExerciseBuilder().build();
        AddExerciseCommand addExerciseCommand = new AddExerciseCommand(validExercise);
        ModelStub modelStub = new ModelStubWithExercise(validExercise);

        assertThrows(CommandException.class,
            AddExerciseCommand.MESSAGE_DUPLICATE_EXERCISE, () -> addExerciseCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Exercise aerobics = new ExerciseBuilder().withName("Aerobics").build();
        Exercise basketball = new ExerciseBuilder().withName("Basketball").build();
        AddExerciseCommand addAliceCommand = new AddExerciseCommand(aerobics);
        AddExerciseCommand addBobCommand = new AddExerciseCommand(basketball);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddExerciseCommand addAliceCommandCopy = new AddExerciseCommand(aerobics);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different exercises -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getExerciseBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExerciseBookFilePath(Path exerciseBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addExercise(Exercise exercise) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExerciseBook(ReadOnlyExerciseBook anotherBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyExerciseBook getExerciseBookData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasExercise(Exercise exercise) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteExercise(Exercise exercise) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setExercise(Exercise target, Exercise editedExercise) {
            throw new AssertionError("This method should not be called.");
        }

        //=======================regime================================================================================
        @Override
        public Path getRegimeBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRegimeBookFilePath(Path exerciseBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addRegime(Regime regime) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRegimeBook(ReadOnlyRegimeBook anotherBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyRegimeBook getAllRegimeData() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasRegime(Regime regime) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteRegime(Regime regime) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setRegime(Regime target, Regime editedRegime) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int getRegimeIndex(Regime regime) {
            throw new AssertionError("This method shuold not be called.");
        }

        @Override
        public ObservableList<Exercise> getFilteredExerciseList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Regime> getFilteredRegimeList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredExerciseList(Predicate<Exercise> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public PropertyManager getPropertyManager() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isPrefixPresent(Prefix prefix) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isFullNamePresent(String fullName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCustomProperty(CustomProperty customProperty) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredRegimeList(Predicate<Regime> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        //=======================suggest================================================================================

        @Override
        public ReadOnlyExerciseBook getDatabaseBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSuggestedExerciseList(Predicate<Exercise> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Exercise> getSuggestedExerciseList() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single exercise.
     */
    private class ModelStubWithExercise extends ModelStub {
        private final Exercise exercise;

        ModelStubWithExercise(Exercise exercise) {
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
     * A Model stub that always accept the exercise being added.
     */
    private class ModelStubAcceptingExerciseAdded extends ModelStub {
        final ArrayList<Exercise> exercisesAdded = new ArrayList<>();

        @Override
        public boolean hasExercise(Exercise exercise) {
            requireNonNull(exercise);
            return exercisesAdded.stream().anyMatch(exercise::isSameExercise);
        }

        @Override
        public void addExercise(Exercise exercise) {
            requireNonNull(exercise);
            exercisesAdded.add(exercise);
        }

        @Override
        public ReadOnlyExerciseBook getExerciseBookData() {
            return new ExerciseBook();
        }
    }

}
