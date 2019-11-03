package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.testutil.Assert.assertThrows;
import static seedu.exercise.testutil.typicalutil.TypicalSchedule.VALID_SCHEDULE_CARDIO_DATE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.MainApp;
import seedu.exercise.commons.core.State;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.logic.commands.exceptions.CommandException;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UniqueResourceList;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Exercise;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.resource.Schedule;
import seedu.exercise.testutil.TestUtil;
import seedu.exercise.testutil.typicalutil.TypicalConflict;
import seedu.exercise.testutil.typicalutil.TypicalExercises;
import seedu.exercise.testutil.typicalutil.TypicalIndexes;
import seedu.exercise.testutil.typicalutil.TypicalRegime;
import seedu.exercise.testutil.typicalutil.TypicalSchedule;
import seedu.exercise.ui.ListResourceType;

/**
 * Contains integration tests (interaction with Model) and unit tests for
 * {@code ResolveCommand}.
 */
public class ResolveCommandTest {

    private final ResolveCommand validResolveCommandTakeFromScheduledWithEmptyIndexes = new ResolveCommand(
        new Name(ResolveCommand.TAKE_FROM_SCHEDULED),
        new ArrayList<>(),
        new ArrayList<>());
    private final ResolveCommand validResolveCommandTakeFromConflictWithEmptyIndexes = new ResolveCommand(
        new Name(ResolveCommand.TAKE_FROM_CONFLICTING),
        new ArrayList<>(),
        new ArrayList<>());
    private final ResolveCommand validResolveCommandWithImpossibleRegimeName = new ResolveCommand(
        new Name("No one is going to call their regime this name seriously"),
        new ArrayList<>(),
        new ArrayList<>());
    private final ResolveCommand validResolveCommandWithNonEmptyIndexesSameName = new ResolveCommand(
        new Name(TypicalRegime.VALID_REGIME_NAME_CARDIO),
        Arrays.asList(TypicalIndexes.INDEX_ONE_BASED_FIRST),
        new ArrayList<>());
    private final ResolveCommand validResolveCommandWithNonEmptyIndexesDifferentName = new ResolveCommand(
        new Name(TypicalRegime.VALID_REGIME_NAME_CHEST),
        Arrays.asList(TypicalIndexes.INDEX_ONE_BASED_FIRST),
        Arrays.asList(TypicalIndexes.INDEX_ONE_BASED_FIRST));
    private final ResolveCommand validResolveCommandWithOutOfBoundIndexes = new ResolveCommand(
        new Name(TypicalRegime.VALID_REGIME_NAME_CARDIO),
        Arrays.asList(TypicalIndexes.INDEX_VERY_LARGE_NUMBER),
        new ArrayList<>());

    private Model model = new ModelManager(new ReadOnlyResourceBook<>(), TypicalRegime.getTypicalRegimeBook(),
        new ReadOnlyResourceBook<>(), TypicalSchedule.getTypicalScheduleBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        //Tests will require state to be in conflict and have a conflict object being held.
        //If required to test other scenarios, set these to desired values in actual tests.
        MainApp.setState(State.IN_CONFLICT);
        model.setConflict(TypicalConflict.VALID_CONFLICT);
    }

    //===================UNIT TESTS==================================================
    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ResolveCommand(
            null, null, null));
    }

    @Test
    public void execute_nullModel_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> validResolveCommandTakeFromScheduledWithEmptyIndexes
                .execute(null));
    }

    @Test
    public void execute_invalidMainAppState_throwsCommandException() {
        MainApp.setState(State.NORMAL);
        assertThrows(CommandException.class, () -> validResolveCommandTakeFromScheduledWithEmptyIndexes
            .execute(new ModelStubForTakingOneSchedule()));
    }

    @Test
    public void execute_indexesOutOfBounds_throwsCommandException() {
        assertThrows(CommandException.class, () -> validResolveCommandWithOutOfBoundIndexes
            .execute(new ModelStubForTakingOneSchedule()));
    }

    @Test
    public void execute_duplicateRegimeName_throwsCommandException() {
        assertThrows(CommandException.class, () -> validResolveCommandWithNonEmptyIndexesSameName
            .execute(new ModelStubWithDuplicateRegimeName()));
    }

    @Test
    public void execute_enteredNameNotFromConflictingSchedule_throwsCommandException() {
        assertThrows(CommandException.class, () -> validResolveCommandWithImpossibleRegimeName
            .execute(new ModelStubForTakingOneSchedule()));
    }

    @Test
    public void execute_validCommand_mainAppStateChange() {
        try {
            validResolveCommandTakeFromScheduledWithEmptyIndexes.execute(new ModelStubForTakingOneSchedule());
            assertEquals(State.NORMAL, MainApp.getState());
        } catch (CommandException e) {
            //Will never reach this block
        }
    }

    @Test
    public void equals_variousScenarios_success() {
        TestUtil.assertCommonEqualsTest(validResolveCommandTakeFromScheduledWithEmptyIndexes);
    }

    //===================INTEGRATION TESTS==================================================
    @Test
    public void execute_validCommandWithExerciseFromScheduled_success() {
        assertResolveCommandTakeFromOneSchedule(ResolveCommand.TAKE_FROM_SCHEDULED);

    }

    @Test
    public void execute_validCommandWithExerciseFromConflicting_success() {
        assertResolveCommandTakeFromOneSchedule(ResolveCommand.TAKE_FROM_CONFLICTING);
    }


    @Test
    public void execute_validCommandWithExerciseFromBothSchedule_success() {
        Conflict conflict = model.getConflict();
        String expectedMessage = String.format(ResolveCommand.MESSAGE_SUCCESS,
            conflict.getScheduledName(), conflict.getConflictedName());
        Model expectedModel = deepCopyModel();

        Regime resolvedRegime = getResolvedRegime();
        Schedule resolvedSchedule = new Schedule(resolvedRegime, conflict.getConflictDate());

        expectedModel.removeSchedule(conflict.getScheduled());
        expectedModel.addSchedule(resolvedSchedule);
        expectedModel.addRegime(resolvedRegime);

        CommandResult expectedResult = new CommandResult(expectedMessage, ListResourceType.SCHEDULE);
        assertCommandSuccess(validResolveCommandWithNonEmptyIndexesDifferentName,
            model, expectedResult, expectedModel);
        assertStateNormal();
    }

    @Test
    public void execute_duplicateRegimeNameWithActualModel_throwsCommandException() {
        String expectedMessage = String.format(ResolveCommand.MESSAGE_DUPLICATE_NAME,
            TypicalRegime.VALID_REGIME_NAME_CARDIO);

        CommandTestUtil.assertCommandFailure(validResolveCommandWithNonEmptyIndexesSameName,
            model, expectedMessage);
    }

    @Test
    public void execute_duplicateExerciseSelectedFromIndex_throwsCommandException() {
        //Special conflict for testing duplcaite exercises from conflict
        model.setConflict(TypicalConflict.VALID_CONFLICT_WITH_DUPLICATE_EXERCISES);
        String expectedMessage = String.format(ResolveCommand.MESSAGE_DUPLICATE_EXERCISE_SELECTED);
        Model expectedModel = deepCopyModel();

        assertCommandFailure(validResolveCommandWithNonEmptyIndexesDifferentName,
            model, expectedMessage);
    }

    private Model deepCopyModel() {
        return new ModelManager(new ReadOnlyResourceBook<>(), model.getAllRegimeData(),
            new ReadOnlyResourceBook<>(), model.getAllScheduleData(), new UserPrefs());
    }

    private void assertStateNormal() {
        assertEquals(State.NORMAL, MainApp.getState());
    }

    private Regime getResolvedRegime() {
        UniqueResourceList<Exercise> resolvedExercises = new UniqueResourceList<>();
        resolvedExercises.setAll(Arrays.asList(TypicalExercises.WALK));
        return new Regime(new Name(TypicalRegime.VALID_REGIME_NAME_CHEST), resolvedExercises);
    }

    @Test
    private void assertResolveCommandTakeFromOneSchedule(String scheduledOrConflicting) {
        Conflict conflict = model.getConflict();
        String expectedMessage = String.format(ResolveCommand.MESSAGE_SUCCESS,
                conflict.getScheduledName(), conflict.getConflictedName());
        CommandResult expectedResult = new CommandResult(expectedMessage, ListResourceType.SCHEDULE);

        Model expectedModel = deepCopyModel();
        expectedModel.removeSchedule(conflict.getScheduled());

        if (scheduledOrConflicting.equals(ResolveCommand.TAKE_FROM_SCHEDULED)) {
            expectedModel.addSchedule(conflict.getScheduled());
            assertCommandSuccess(validResolveCommandTakeFromScheduledWithEmptyIndexes,
                    model, expectedResult, expectedModel);
        } else {
            expectedModel.addSchedule(conflict.getConflicted());
            assertCommandSuccess(validResolveCommandTakeFromConflictWithEmptyIndexes,
                    model, expectedResult, expectedModel);
        }
        assertStateNormal();
    }


    /**
     * Stub that has a default conflict to resolve.
     * Stub will always return true when there is a call to {@code hasRegime}.
     * Stub will do nothing in resolving actual conflict.
     */
    private class ModelStubWithDuplicateRegimeName extends ModelStub {

        private Conflict defaultConflict = TypicalConflict.VALID_CONFLICT;

        @Override
        public Conflict getConflict() {
            return defaultConflict;
        }

        @Override
        public boolean hasRegime(Regime regime) {
            return true;
        }

        @Override
        public Schedule resolveConflict(Name regimeName, List<Index> indexFromSchedule, List<Index> indexFromConflict) {
            return null;
        }
    }

    /**
     * Stub that has a default conflict to resolve.
     * Stub will always return false when there is a call to {@code hasRegime}
     * Stub will do nothing in resolving actual conflict
     */
    private class ModelStubForTakingOneSchedule extends ModelStub {

        private Conflict defaultConflict = TypicalConflict.VALID_CONFLICT;

        @Override
        public Conflict getConflict() {
            return defaultConflict;
        }

        @Override
        public boolean hasRegime(Regime regime) {
            return false;
        }

        @Override
        public Schedule resolveConflict(Name regimeName, List<Index> indexFromSchedule, List<Index> indexFromConflict) {
            return VALID_SCHEDULE_CARDIO_DATE;
        }

        @Override
        public boolean isSelectedIndexesFromRegimeDuplicate(List<Index> scheduledIndex, List<Index> conflictingIndex) {
            return false;
        }
    }
}
