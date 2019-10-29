package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.exercise.testutil.Assert.assertThrows;

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
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.conflict.Conflict;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.model.util.DefaultPropertyBookUtil;
import seedu.exercise.testutil.typicalutil.TypicalConflict;
import seedu.exercise.testutil.typicalutil.TypicalIndexes;
import seedu.exercise.testutil.typicalutil.TypicalRegime;
import seedu.exercise.testutil.typicalutil.TypicalSchedule;

/**
 * Contains integration tests (interaction with Model) and unit tests for
 * {@code ResolveCommand}.
 */
public class ResolveCommandTest {

    private final ResolveCommand validResolveCommandWithEmptyIndexes = new ResolveCommand(
            new Name(TypicalRegime.VALID_REGIME_NAME_CARDIO),
            new ArrayList<>(),
            new ArrayList<>());
    private final ResolveCommand validResolveCommandWithImpossibleRegimeName = new ResolveCommand(
            new Name("No one is going to call their regime this name seriously"),
            new ArrayList<>(),
            new ArrayList<>());
    private final ResolveCommand validResolveCommandWithNonEmptyIndexes = new ResolveCommand(
            new Name(TypicalRegime.VALID_REGIME_NAME_CARDIO),
            Arrays.asList(TypicalIndexes.INDEX_ONE_BASED_FIRST),
            new ArrayList<>());
    private final ResolveCommand validResolveCommandWithOutOfBoundIndexes = new ResolveCommand(
            new Name(TypicalRegime.VALID_REGIME_NAME_CARDIO),
            Arrays.asList(TypicalIndexes.INDEX_VERY_LARGE_NUMBER),
            new ArrayList<>());

    private Model model = new ModelManager(new ReadOnlyResourceBook<>(), TypicalRegime.getTypicalRegimeBook(),
            new ReadOnlyResourceBook<>(), TypicalSchedule.getTypicalScheduleBook(), new UserPrefs(),
            DefaultPropertyBookUtil.getDefaultPropertyBook());

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
        assertThrows(NullPointerException.class, () -> validResolveCommandWithEmptyIndexes.execute(null));
    }

    @Test
    public void execute_invalidMainAppState_throwsCommandException() {
        MainApp.setState(State.NORMAL);
        assertThrows(CommandException.class, () -> validResolveCommandWithEmptyIndexes
                .execute(new ModelStubForTakingOneSchedule()));
    }

    @Test
    public void execute_indexesOutOfBounds_throwsCommandExceptino() {
        assertThrows(CommandException.class, () -> validResolveCommandWithOutOfBoundIndexes
                .execute(new ModelStubForTakingOneSchedule()));
    }

    @Test
    public void execute_duplicateRegimeName_throwsCommandException() {
        assertThrows(CommandException.class, () -> validResolveCommandWithNonEmptyIndexes
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
            validResolveCommandWithEmptyIndexes.execute(new ModelStubForTakingOneSchedule());
            assertEquals(State.NORMAL, MainApp.getState());
        } catch (CommandException e) {
            //Will never reach this block
        }
    }

    //===================INTEGRATION TESTS==================================================
    @Test
    public void execute_validCommand_success() {
        Conflict conflict = model.getConflict();
        String expectedMessage = String.format(ResolveCommand.MESSAGE_SUCCESS,
                conflict.getScheduledName(), conflict.getConflictedName());

        Model expectedModel = new ModelManager(new ReadOnlyResourceBook<>(), model.getAllRegimeData(),
            new ReadOnlyResourceBook<>(), model.getAllScheduleData(), new UserPrefs(),
            DefaultPropertyBookUtil.getDefaultPropertyBook());
        expectedModel.removeSchedule(conflict.getScheduled());
        expectedModel.addSchedule(conflict.getScheduled());

        CommandTestUtil.assertCommandSuccess(validResolveCommandWithEmptyIndexes,
                model, expectedMessage, expectedModel);
        assertEquals(State.NORMAL, MainApp.getState());
    }

    @Test
    public void execute_duplicateRegimeNameWithActualModel_throwsCommandException() {
        String expectedMessage = String.format(ResolveCommand.MESSAGE_DUPLICATE_NAME,
                TypicalRegime.VALID_REGIME_NAME_CARDIO);

        CommandTestUtil.assertCommandFailure(validResolveCommandWithNonEmptyIndexes,
                model, expectedMessage);
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
        public void resolveConflict(Name regimeName, List<Index> indexFromSchedule, List<Index> indexFromConflict) {

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
        public void resolveConflict(Name regimeName, List<Index> indexFromSchedule, List<Index> indexFromConflict) {

        }
    }
}
