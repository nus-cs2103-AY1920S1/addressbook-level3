package seedu.moneygowhere.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.moneygowhere.logic.commands.CommandTestUtil.assertCommandSuccess;
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


/**
 * Contains integration tests (interaction with the Model) and unit tests for StatsCommand.
 */
class StatsCommandTest {

    private Model model = new ModelManager(getTypicalSpendingBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalSpendingBook(), new UserPrefs());

    //Testing logic of calculating totalCost, budget and budgetRemaining
    //Testing whether list has been filtered correctly (no date parameters specified)

    @Test
    public void execute_statsMessage_success() {
        CommandResult expectedCommandResult = new CommandResult("Statistics for all dates\n", false, true, false);
        assertCommandSuccess(new StatsCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_statsMessageValidDateRange_success() {
        CommandResult expectedCommandResult = new CommandResult(
            String.format("Statistics for spending between %s and %s\n",
            APPLE.getDate(), GLASSES.getDate()), false, true, false);
        assertCommandSuccess(new StatsCommand(APPLE.getDate(), GLASSES.getDate()), model,
            expectedCommandResult, expectedModel);
    }

    @Test
    public void getStatsPredicate_noDateRange_success() {
        Predicate<Spending> expectedPredicate = PREDICATE_SHOW_ALL_SPENDINGS;
        assertEquals(expectedPredicate, new StatsCommand().getStatsPredicate());
    }

    @Test
    public void getStatsPredicate_validDateRange_success() {
        Date startDate = APPLE.getDate();
        Date endDate = BANANA.getDate();
        Predicate<Spending> expectedPredicate = s-> {
            return s.getDate().value.compareTo(startDate.value) >= 0
                && s.getDate().value.compareTo(endDate.value) <= 0;
        };
        model.updateFilteredSpendingList(new StatsCommand(APPLE.getDate(), BANANA.getDate()).getStatsPredicate());
        expectedModel.updateFilteredSpendingList(expectedPredicate);
        assertEquals(model.getFilteredSpendingList(), expectedModel.getFilteredSpendingList());
    }
}
