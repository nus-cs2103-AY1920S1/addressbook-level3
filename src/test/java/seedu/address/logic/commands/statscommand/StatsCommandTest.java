package seedu.address.logic.commands.statscommand;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.statisticcommand.StatsCommand.MESSAGE_USAGE;
import static seedu.address.statistic.TypicalStatsPayload.ENDING_DATE_2019;
import static seedu.address.statistic.TypicalStatsPayload.STARTING_DATE_2019;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.commands.statisticcommand.StatisticType;
import seedu.address.logic.commands.statisticcommand.StatsCommand;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;
import seedu.address.statistic.StatsPayload;

public class StatsCommandTest {

    private Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
            getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

    @Test
    public void execute_commandResult() {
        StatsCommand testStatCommand = new StatsCommand(STARTING_DATE_2019, ENDING_DATE_2019, StatisticType.COST);
        StatsPayload expectedPayload = new StatsPayload(STARTING_DATE_2019, ENDING_DATE_2019, StatisticType.COST);
        CommandResult expectedResult = new CommandResult(MESSAGE_USAGE, expectedPayload, UiChange.STATS);
        try {
            assertTrue(expectedResult.equals(
                    testStatCommand.execute(model, new CommandHistory(), new UndoRedoStack())));
        } catch (CommandException e) {
            e.printStackTrace();
        }
    }

}
