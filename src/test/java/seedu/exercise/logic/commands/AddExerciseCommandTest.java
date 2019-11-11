package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.logic.commands.statistic.StatsFactory;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.testutil.builder.ExerciseBuilder;
import seedu.exercise.testutil.builder.StatisticBuilder;

public class AddExerciseCommandTest {

    @Test
    public void constructor_nullExercise_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddExerciseCommand(null));
    }

    @Test
    public void execute_exerciseAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingExerciseAdded modelStub = new ModelStubAcceptingExerciseAdded();
        Exercise validExercise = new ExerciseBuilder().build();

        CommandResult commandResult = new AddExerciseCommand(validExercise).execute(modelStub);

        assertEquals(String.format(AddExerciseCommand.MESSAGE_SUCCESS, validExercise),
            commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validExercise), modelStub.exercisesAdded);
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
            return this.exercise.isSameResource(exercise);
        }
    }

    /**
     * A Model stub that always accept the exercise being added.
     */
    private class ModelStubAcceptingExerciseAdded extends ModelStub {
        final ArrayList<Exercise> exercisesAdded = new ArrayList<>();
        final Statistic statistic = new StatisticBuilder().build();

        @Override
        public boolean hasExercise(Exercise exercise) {
            requireNonNull(exercise);
            return exercisesAdded.stream().anyMatch(exercise::isSameResource);
        }

        @Override
        public void addExercise(Exercise exercise) {
            requireNonNull(exercise);
            exercisesAdded.add(exercise);
        }

        @Override
        public ReadOnlyResourceBook<Exercise> getExerciseBookData() {
            return new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR);
        }

        @Override
        public void updateStatistic() {
            ReadOnlyResourceBook<Exercise> exercises = getExerciseBookData();
            Statistic outdatedStatistic = getStatistic();
            StatsFactory statsFactory = new StatsFactory(exercises, outdatedStatistic.getChart(),
                outdatedStatistic.getCategory(), outdatedStatistic.getStartDate(), outdatedStatistic.getEndDate());
            Statistic statistic = statsFactory.generateStatistic();
            this.statistic.resetData(statistic);
        }

        @Override
        public void setStatistic(Statistic statistic) {
            this.statistic.resetData(statistic);
        }

        @Override
        public Statistic getStatistic() {
            StatisticBuilder statisticBuilder = new StatisticBuilder();
            return statisticBuilder.build();
        }
    }

}
