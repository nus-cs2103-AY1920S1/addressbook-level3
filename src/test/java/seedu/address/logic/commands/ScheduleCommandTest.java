package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

public class ScheduleCommandTest {

    @Test
    public void execute_schedule_success() {

        Calendar calendar = Calendar.getInstance();

        Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(), getTypicalOrderBook(),
                getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(), getTypicalOrderBook(),
                getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        assertCommandSuccess(new ScheduleCommand(calendar), model, ScheduleCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
