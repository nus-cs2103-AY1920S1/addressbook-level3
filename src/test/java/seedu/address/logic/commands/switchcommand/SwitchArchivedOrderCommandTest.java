package seedu.address.logic.commands.switchcommand;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

public class SwitchArchivedOrderCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(),
                getTypicalOrderBook(), getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        expectedModel = new ModelManager(model.getCustomerBook(), model.getPhoneBook(),
                model.getOrderBook(), model.getScheduleBook(), new DataBook<Order>(), new UserPrefs());
    }


    @Test
    public void execute_switchPanel() {
        assertCommandSuccess(new SwitchArchivedOrderPanelCommand(), model,
                SwitchArchivedOrderPanelCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
