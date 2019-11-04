package seedu.exercise.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.exercise.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_EXERCISE_COMPARATOR;
import static seedu.exercise.model.resource.ResourceComparator.DEFAULT_SCHEDULE_COMPARATOR;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.DUPLICATE_REGIME_INDEXES;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.LARGE_REGIME_INDEX;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.VALID_REGIME_CARDIO;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.VALID_REGIME_NAME_CARDIO;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.VALID_REGIME_NAME_CHEST;
import static seedu.exercise.testutil.typicalutil.TypicalRegime.getTypicalRegimeBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.exercise.commons.core.Messages;
import seedu.exercise.commons.core.index.Index;
import seedu.exercise.model.Model;
import seedu.exercise.model.ModelManager;
import seedu.exercise.model.ReadOnlyResourceBook;
import seedu.exercise.model.UserPrefs;
import seedu.exercise.model.property.Name;
import seedu.exercise.model.resource.Regime;
import seedu.exercise.ui.ListResourceType;

public class DeleteRegimeCommandTest {

    private Model model = new ModelManager(new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
            getTypicalRegimeBook(),
        new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
            new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR), new UserPrefs());

    @Test
    public void execute_validRegimeName_success() {
        int regimeIndex = model.getRegimeIndex(VALID_REGIME_CARDIO);
        Regime regimeToDelete = model.getSortedRegimeList().get(regimeIndex);
        Name name = new Name(VALID_REGIME_NAME_CARDIO);
        DeleteRegimeCommand deleteRegimeCommand = new DeleteRegimeCommand(name, null);

        String expectedMessage = String.format(DeleteRegimeCommand.MESSAGE_DELETE_REGIME_SUCCESS,
            name.toString(), regimeToDelete);

        ModelManager expectedModel = new ModelManager(new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                model.getAllRegimeData(),
                new ReadOnlyResourceBook<>(DEFAULT_EXERCISE_COMPARATOR),
                new ReadOnlyResourceBook<>(DEFAULT_SCHEDULE_COMPARATOR), new UserPrefs());
        expectedModel.deleteRegime(regimeToDelete);

        CommandResult expectedCommandResult = new CommandResult(expectedMessage, ListResourceType.REGIME);
        assertCommandSuccess(deleteRegimeCommand, model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_invalidRegimeName_throwsCommandException() {
        Name invalidName = new Name("invalid");
        DeleteRegimeCommand deleteRegimeCommand = new DeleteRegimeCommand(invalidName, null);

        assertCommandFailure(deleteRegimeCommand, model, DeleteRegimeCommand.MESSAGE_REGIME_DOES_NOT_EXIST);
    }

    @Test
    public void execute_duplicateIndex_throwsCommandException() {
        Name name = new Name(VALID_REGIME_NAME_CARDIO);
        DeleteRegimeCommand deleteRegimeCommand = new DeleteRegimeCommand(name, DUPLICATE_REGIME_INDEXES);

        assertCommandFailure(deleteRegimeCommand, model, DeleteRegimeCommand.MESSAGE_DUPLICATE_INDEX);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Name name = new Name(VALID_REGIME_NAME_CARDIO);
        DeleteRegimeCommand deleteRegimeCommand = new DeleteRegimeCommand(name, LARGE_REGIME_INDEX);

        assertCommandFailure(deleteRegimeCommand, model, Messages.MESSAGE_INVALID_EXERCISE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Name name = new Name(VALID_REGIME_NAME_CARDIO);
        List<Index> indexes = new ArrayList<>();
        DeleteRegimeCommand deleteRegimeCommand = new DeleteRegimeCommand(name, indexes);

        // same object -> returns true
        assertTrue(deleteRegimeCommand.equals(deleteRegimeCommand));

        // same values -> returns true
        DeleteRegimeCommand deleteRegimeCommandCopy = new DeleteRegimeCommand(name, indexes);
        assertTrue(deleteRegimeCommand.equals(deleteRegimeCommandCopy));

        // different types -> returns false
        assertFalse(deleteRegimeCommand.equals(1));

        // null -> returns false
        assertFalse(deleteRegimeCommand.equals(null));

        // different name -> returns false
        assertFalse(deleteRegimeCommand.equals(new DeleteRegimeCommand(new Name(VALID_REGIME_NAME_CHEST), indexes)));

        //different indexes -> returns false
        assertFalse(deleteRegimeCommand.equals(new DeleteRegimeCommand(name, null)));
    }
}
