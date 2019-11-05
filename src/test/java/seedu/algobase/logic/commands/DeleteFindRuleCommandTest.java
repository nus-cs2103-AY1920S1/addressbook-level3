package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.algobase.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.algobase.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.algobase.testutil.TypicalProblemSearchRules.getTypicalAlgoBase;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.findrule.DeleteFindRuleCommand;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteFindRuleCommand}.
 */
class DeleteFindRuleCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndex_success() {
        ProblemSearchRule ruleToDelete = model.getFilteredFindRuleList().get(INDEX_FIRST.getZeroBased());
        DeleteFindRuleCommand command = new DeleteFindRuleCommand(INDEX_FIRST);

        String expectedMessage = String.format(DeleteFindRuleCommand.MESSAGE_SUCCESS, ruleToDelete.getName());

        ModelManager expectedModel = new ModelManager(model.getAlgoBase(), new UserPrefs());
        expectedModel.deleteFindRule(ruleToDelete);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFindRuleList().size() + 1);
        DeleteFindRuleCommand command = new DeleteFindRuleCommand(outOfBoundIndex);

        assertCommandFailure(command, model, commandHistory, Messages.MESSAGE_INVALID_FIND_RULE_DISPLAYED_INDEX);
    }


    @Test
    void equals() {
        DeleteFindRuleCommand deleteFirstCommand = new DeleteFindRuleCommand(INDEX_FIRST);
        DeleteFindRuleCommand deleteSecondCommand = new DeleteFindRuleCommand(INDEX_SECOND);

        // same object -> returns true
        assertEquals(deleteFirstCommand, deleteFirstCommand);

        // same values -> returns true
        DeleteFindRuleCommand deleteFirstCommandCopy = new DeleteFindRuleCommand(INDEX_FIRST);
        assertEquals(deleteFirstCommand, deleteFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, deleteFirstCommand);

        // null -> returns false
        assertNotEquals(null, deleteFirstCommand);

        // different person -> returns false
        assertNotEquals(deleteFirstCommand, deleteSecondCommand);
    }
}
