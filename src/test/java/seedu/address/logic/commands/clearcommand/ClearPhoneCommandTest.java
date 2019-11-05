package seedu.address.logic.commands.clearcommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

public class ClearPhoneCommandTest {

    @Test
    public void execute_emptyPhoneBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearPhoneCommand(), model, ClearPhoneCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyPhoneBook_success() throws CommandException {
        Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        new ClearPhoneCommand().execute(expectedModel, new CommandHistory(), new UndoRedoStack());

        assertCommandSuccess(new ClearPhoneCommand(), model, ClearPhoneCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
