package seedu.exercise.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_REGIME_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.typicalutil.TypicalExercises.getTypicalExerciseBook;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.DUPLICATE_REGIME_INDEXES;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.LARGE_REGIME_INDEX;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.VALID_REGIME_CARDIO;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.VALID_REGIME_INDEXES;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.VALID_REGIME_NAME_CARDIO;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.getTypicalRegimeBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.logic.commands.statistic.Statistic;
import seedu.exercise.logic.commands.statistic.StatsFactory;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.SortedUniqueResourceList;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.testutil.builder.StatisticBuilder;

//@@author jietung
public class AddRegimeCommandTest {

    @Test
    public void constructor_nullName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddRegimeCommand(new ArrayList<>(), null));
    }

    @Test
    public void constructor_nullIndexes_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddRegimeCommand(null, new Name("test")));
    }

    @Test
    public void execute_duplicateIndex_throwsCommandException() {
        Model model = new ModelManager(new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                getTypicalRegimeBook(),
            new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR),
                new UserPrefs());
        Name name = new Name(VALID_REGIME_NAME_CARDIO);
        DeleteRegimeCommand deleteRegimeCommand = new DeleteRegimeCommand(name, DUPLICATE_REGIME_INDEXES);

        assertCommandFailure(deleteRegimeCommand, model, DeleteRegimeCommand.MESSAGE_DUPLICATE_INDEX);
    }

    @Test
    public void execute_regimeAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingRegimeAdded modelStub = new ModelStubAcceptingRegimeAdded();
        Name validName = new Name("test");
        ArrayList<Index> validIndexes = new ArrayList<>();
        Regime validRegime = new Regime(validName,
                new SortedUniqueResourceList<>(DEFAULT_EXERCISE_COMPARATOR));

        CommandResult commandResult = new AddRegimeCommand(validIndexes, validName).execute(modelStub);

        assertEquals(AddRegimeCommand.MESSAGE_SUCCESS_NEW_REGIME, commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validRegime), modelStub.regimesAdded);
    }

    @Test
    public void execute_duplicateExerciseInRegime_throwsCommandException() {
        Name existingName = new Name(VALID_REGIME_NAME_CARDIO);

        ModelStub modelStub = new ModelStubWithRegime();
        AddRegimeCommand addRegimeCommand = new AddRegimeCommand(VALID_REGIME_INDEXES, existingName);

        assertThrows(CommandException.class,
            AddRegimeCommand.MESSAGE_DUPLICATE_EXERCISE_IN_REGIME, () -> addRegimeCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateIndexes_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithRegime();
        AddRegimeCommand addRegimeCommand =
            new AddRegimeCommand(DUPLICATE_REGIME_INDEXES, new Name(VALID_REGIME_NAME_CARDIO));

        assertThrows(CommandException.class,
            AddRegimeCommand.MESSAGE_DUPLICATE_INDEX, () -> addRegimeCommand.execute(modelStub));
    }

    @Test
    public void execute_emptyIndexes_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithRegime();
        AddRegimeCommand addRegimeCommand =
            new AddRegimeCommand(new ArrayList<Index>(), new Name(VALID_REGIME_NAME_CARDIO));

        assertThrows(CommandException.class,
            AddRegimeCommand.MESSAGE_NO_EXERCISES_ADDED, () -> addRegimeCommand.execute(modelStub));
    }

    @Test
    public void execute_invalidIndexes_throwsCommandException() {
        ModelStub modelStub = new ModelStubWithRegime();
        AddRegimeCommand addRegimeCommand =
            new AddRegimeCommand(LARGE_REGIME_INDEX, new Name(VALID_REGIME_NAME_CARDIO));

        assertThrows(CommandException.class,
            Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX, () -> addRegimeCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Name name = new Name(VALID_REGIME_NAME_CARDIO);
        List<Index> indexes = VALID_REGIME_INDEXES;
        AddRegimeCommand addRegimeCommand = new AddRegimeCommand(indexes, name);

        // same object -> returns true
        assertTrue(addRegimeCommand.equals(addRegimeCommand));

        // same values -> returns true
        AddRegimeCommand addRegimeCommandCopy = new AddRegimeCommand(indexes, name);
        assertTrue(addRegimeCommand.equals(addRegimeCommandCopy));

        // different name -> returns true
        assertTrue(addRegimeCommand.equals(new AddRegimeCommand(indexes, new Name(VALID_REGIME_NAME_CARDIO))));

        // different types -> returns false
        assertFalse(addRegimeCommand.equals(1));

        // null -> returns false
        assertFalse(addRegimeCommand.equals(null));

        //different indexes -> returns false
        assertFalse(addRegimeCommand.equals(new AddRegimeCommand(new ArrayList<>(), name)));
    }

    /**
     * A Model stub that contains a single regime with exercise.
     */
    private class ModelStubWithRegime extends ModelStub {
        private Regime regime = VALID_REGIME_CARDIO;
        private ReadOnlyResourceBook<Exercise> exercises = getTypicalExerciseBook();
        private ReadOnlyResourceBook<Regime> regimes = getTypicalRegimeBook();

        @Override
        public int getRegimeIndex(Regime regime) {
            return regimes.getResourceIndex(regime);
        }

        @Override
        public boolean hasRegime(Regime regime) {
            requireNonNull(regime);
            return this.regime.isSameResource(regime);
        }

        @Override
        public void setRegime(Regime regime, Regime targetedRegime) {
        }

        @Override
        public ObservableList<Exercise> getSortedExerciseList() {
            return new FilteredList<>(exercises.getSortedResourceList());
        }

        @Override
        public ObservableList<Regime> getSortedRegimeList() {
            return new FilteredList<>(regimes.getSortedResourceList());
        }
    }

    /**
     * A Model stub that always accept the regime being added.
     */
    private class ModelStubAcceptingRegimeAdded extends ModelStub {
        private ReadOnlyResourceBook<Exercise> exercises = getTypicalExerciseBook();
        private ArrayList<Regime> regimesAdded = new ArrayList<>();
        private Statistic statistic = new StatisticBuilder().build();

        @Override
        public boolean hasRegime(Regime regime) {
            requireNonNull(regime);
            return regimesAdded.stream().anyMatch(regime::isSameResource);
        }

        @Override
        public void addRegime(Regime regime) {
            requireNonNull(regime);
            regimesAdded.add(regime);
        }

        @Override
        public ReadOnlyResourceBook<Regime> getAllRegimeData() {
            return new ReadOnlyResourceBook<>(DEFAULT_REGIME_COMPARATOR);
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
        public ObservableList<Exercise> getSortedExerciseList() {
            return new FilteredList<>(exercises.getSortedResourceList());
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
