package seedu.algobase.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.algobase.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.algobase.testutil.TypicalIndexes.INDEX_FIRST;
import static seedu.algobase.testutil.TypicalIndexes.INDEX_SECOND;
import static seedu.algobase.testutil.TypicalProblemSearchRules.getTypicalAlgoBase;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.algobase.commons.core.Messages;
import seedu.algobase.commons.core.index.Index;
import seedu.algobase.logic.CommandHistory;
import seedu.algobase.logic.commands.findrule.ApplyCommand;
import seedu.algobase.model.Model;
import seedu.algobase.model.ModelManager;
import seedu.algobase.model.UserPrefs;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;

class ApplyCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();
    private Model model = new ModelManager(getTypicalAlgoBase(), new UserPrefs());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_validIndex_success() {
        ProblemSearchRule ruleToApply = model.getFilteredFindRuleList().get(INDEX_FIRST.getZeroBased());
        ApplyCommand command = new ApplyCommand(INDEX_FIRST);

        String expectedMessage = String.format(ApplyCommand.MESSAGE_SUCCESS, ruleToApply.getName());

        ModelManager expectedModel = new ModelManager(model.getAlgoBase(), new UserPrefs());
        Predicate<Problem> findProblemPredicate = ruleToApply.getFindProblemPredicate();
        expectedModel.updateFilteredProblemList(findProblemPredicate);

        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredFindRuleList().size() + 1);
        ApplyCommand command = new ApplyCommand(outOfBoundIndex);

        assertCommandFailure(command, model, commandHistory, Messages.MESSAGE_INVALID_FIND_RULE_DISPLAYED_INDEX);
    }

    @Test
    void equals() {
        ApplyCommand applyFirstCommand = new ApplyCommand(INDEX_FIRST);
        ApplyCommand applySecondCommand = new ApplyCommand(INDEX_SECOND);

        // same object -> returns true
        assertEquals(applyFirstCommand, applyFirstCommand);

        // same values -> returns true
        ApplyCommand applyFirstCommandCopy = new ApplyCommand(INDEX_FIRST);
        assertEquals(applyFirstCommand, applyFirstCommandCopy);

        // different types -> returns false
        assertNotEquals(1, applyFirstCommand);

        // null -> returns false
        assertNotEquals(null, applyFirstCommand);

        // different person -> returns false
        assertNotEquals(applyFirstCommand, applySecondCommand);
    }
}
