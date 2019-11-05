package seedu.address.logic.commands.clearcommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

public class ClearArchivedOrderCommandTest {

    @Test
    public void execute_emptyArchivedOrderBook_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();

        assertCommandSuccess(new ClearArchivedOrderCommand(), model,
                ClearArchivedOrderCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyArchivedOrderBook_success() {
        Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        expectedModel.setArchivedOrderBook(new DataBook<Order>());

        assertCommandSuccess(new ClearArchivedOrderCommand(), model,
                ClearArchivedOrderCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
