package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

public class HistoryCommandTest {

    @Test
    public void execute_emptyCommandHistory_success() {
        Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(), getTypicalOrderBook(),
            getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(), getTypicalOrderBook(),
                getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        assertCommandSuccess(new HistoryCommand(), model, CommandHistory.getCommandHistory(),
                HistoryCommand.MESSAGE_FAILURE, expectedModel);
    }

    @Test
    public void execute_nonEmptyCommandHistory_success() {
        Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(), getTypicalOrderBook(),
                getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(), getTypicalOrderBook(),
                getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        CommandHistory commandHistory = CommandHistory.getCommandHistory();

        String firstCommand = "clear-c";
        commandHistory.add("clear-c");

        assertCommandSuccess(new HistoryCommand(), model, commandHistory,
                    String.format(HistoryCommand.MESSAGE_SUCCESS, firstCommand), expectedModel);

    }

}
