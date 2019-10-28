package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.moneygowhere.logic.commands.GraphCommand.MESSAGE_SUCCESS;
import static seedu.moneygowhere.model.Model.PREDICATE_SHOW_ALL_SPENDINGS;
import static seedu.moneygowhere.testutil.TypicalSpendings.APPLE;
import static seedu.moneygowhere.testutil.TypicalSpendings.BANANA;
import static seedu.moneygowhere.testutil.TypicalSpendings.GLASSES;
import static seedu.moneygowhere.testutil.TypicalSpendings.getTypicalSpendingBook;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.moneygowhere.model.Model;
import seedu.moneygowhere.model.ModelManager;
import seedu.moneygowhere.model.UserPrefs;
import seedu.moneygowhere.model.spending.Date;
import seedu.moneygowhere.model.spending.Spending;

public class GraphCommandTest {
    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    @Test
    public void execute_graphMessage_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, true, false, false);
        assertCommandSuccess(new GraphCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_graphMessageValidDateRange_success() {
        CommandResult expectedCommandResult = new CommandResult(MESSAGE_SUCCESS, true, false, false);
        assertCommandSuccess(new GraphCommand(APPLE.getDate(), GLASSES.getDate()), model,
            expectedCommandResult, expectedModel);
    }

    @Test
    public void getGraphPredicate_noDateRange_success() {
        Predicate<Spending> expectedPredicate = PREDICATE_SHOW_ALL_SPENDINGS;
        assertEquals(expectedPredicate, new GraphCommand().getGraphPredicate());
    }

    @Test
    public void getGraphPredicate_validDateRange_success() {
        Date startDate = APPLE.getDate();
        Date endDate = BANANA.getDate();
        Predicate<Spending> expectedPredicate = s-> {
            return s.getDate().value.compareTo(startDate.value) >= 0
                && s.getDate().value.compareTo(endDate.value) <= 0;
        };
        model.updateFilteredSpendingList(new GraphCommand(APPLE.getDate(), BANANA.getDate()).getGraphPredicate());
        expectedModel.updateFilteredSpendingList(expectedPredicate);
        assertEquals(model.getFilteredSpendingList(), expectedModel.getFilteredSpendingList());
    }
}
