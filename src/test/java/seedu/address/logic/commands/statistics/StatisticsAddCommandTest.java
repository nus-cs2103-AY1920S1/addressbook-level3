package seedu.address.logic.commands.statistics;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.statistics.Statistics;
import seedu.address.testutil.statistics.TypicalStatistics;

public class StatisticsAddCommandTest {

    private static final String PRINTABLE_NAME = "Sample Printable";

    private Model model = new ModelManager();
    private TypicalStatistics typicalStatistics = new TypicalStatistics();

    public StatisticsAddCommandTest() {
        model.addStatistics(typicalStatistics.getTypicalStatistics());
    }

    @Test
    public void execute_typicalStatistics_success() {
        Statistics validStatistics = new TypicalStatistics().getTypicalStatistics();
        StatisticsAddCommand addCommand = new StatisticsAddCommand(validStatistics, "");

        String expectedMessage = String.format(StatisticsAddCommand.MESSAGE_SUCCESS, validStatistics);
        CommandResultType statisticsCommandResult = CommandResultType.SHOW_STATISTIC;
        CommandResult result = new CommandResult(expectedMessage, statisticsCommandResult);

        ModelManager expectedModel = new ModelManager();
        expectedModel.addStatistics(typicalStatistics.getTypicalStatistics());

        assertCommandSuccess(addCommand, model, result, expectedModel);
    }

    @Test
    public void execute_emptyStatistics_success() {
        Statistics validStatistics = new TypicalStatistics().getEmptyDataStatistics();
        StatisticsAddCommand addCommand = new StatisticsAddCommand(validStatistics, "");

        String expectedMessage = String.format(StatisticsAddCommand.MESSAGE_SUCCESS, validStatistics);
        CommandResultType statisticsCommandResult = CommandResultType.SHOW_STATISTIC;
        CommandResult result = new CommandResult(expectedMessage, statisticsCommandResult);

        ModelManager expectedModel = new ModelManager();
        expectedModel.addStatistics(typicalStatistics.getEmptyDataStatistics());

        assertCommandSuccess(addCommand, model, result, expectedModel);
    }

    @Test
    public void execute_singleValueStatistics_success() {
        Statistics validStatistics = new TypicalStatistics().getSingleValueStatistics();
        StatisticsAddCommand addCommand = new StatisticsAddCommand(validStatistics, "");

        String expectedMessage = String.format(StatisticsAddCommand.MESSAGE_SUCCESS, validStatistics);
        CommandResultType statisticsCommandResult = CommandResultType.SHOW_STATISTIC;
        CommandResult result = new CommandResult(expectedMessage, statisticsCommandResult);

        ModelManager expectedModel = new ModelManager();
        expectedModel.addStatistics(typicalStatistics.getSingleValueStatistics());

        assertCommandSuccess(addCommand, model, result, expectedModel);
    }

    @Test
    public void execute_nullStatistics_throwsNullPointerException() {
        Assertions.assertThrows(NullPointerException.class, () ->
            new StatisticsAddCommand(new TypicalStatistics().getNullDataStatistics(), ""));
    }

    @Test
    public void equals() {
        Statistics stats = new TypicalStatistics().getTypicalStatistics();
        Statistics otherStats = new TypicalStatistics().getSingleValueStatistics();
        StatisticsAddCommand addCommand = new StatisticsAddCommand(stats, PRINTABLE_NAME);
        StatisticsAddCommand otherAddCommand = new StatisticsAddCommand(otherStats, PRINTABLE_NAME);

        // same object -> returns true
        Assertions.assertTrue(addCommand.equals(addCommand));

        // same values -> returns true
        StatisticsAddCommand addCommandCopy = new StatisticsAddCommand(stats, PRINTABLE_NAME);
        Assertions.assertTrue(addCommand.equals(addCommandCopy));

        // different types -> returns false
        assertFalse(addCommand.equals(1));

        // null -> returns false
        assertFalse(addCommandCopy.equals(null));

        // different note -> returns false
        assertFalse(addCommandCopy.equals(otherAddCommand));
    }
}
