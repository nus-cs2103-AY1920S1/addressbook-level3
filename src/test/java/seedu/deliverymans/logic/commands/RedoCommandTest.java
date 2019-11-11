package seedu.deliverymans.logic.commands;

import static seedu.deliverymans.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.deliverymans.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.deliverymans.model.util.SampleDataUtil.getSampleCustomerDatabase;
import static seedu.deliverymans.model.util.SampleDataUtil.getSampleDeliverymenDatabase;
import static seedu.deliverymans.model.util.SampleDataUtil.getSampleOrderDatabase;
import static seedu.deliverymans.model.util.SampleDataUtil.getSampleRestaurantDatabase;

import org.junit.jupiter.api.Test;

import seedu.deliverymans.logic.commands.universal.RedoCommand;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.ModelManager;
import seedu.deliverymans.model.Name;
import seedu.deliverymans.model.Phone;
import seedu.deliverymans.model.UserPrefs;
import seedu.deliverymans.model.customer.Address;
import seedu.deliverymans.model.customer.Customer;


class RedoCommandTest {
    private static final String REDONE_COMMAND_TEXT = "add someone";

    private Model model = new ModelManager(getSampleCustomerDatabase(), getSampleDeliverymenDatabase(),
            getSampleRestaurantDatabase(), getSampleOrderDatabase(), new UserPrefs());
    private Customer validCustomer = new Customer(new Name("user"), new Name("name"), new Phone("91234555"),
            new Address("address"));

    @Test
    void execute_hasUndo_success() {
        model.addCustomer(validCustomer);

        String expectedMessage = String.format(RedoCommand.MESSAGE_SUCCESS, REDONE_COMMAND_TEXT);

        Model updatedModel = new ModelManager(getSampleCustomerDatabase(), getSampleDeliverymenDatabase(),
                getSampleRestaurantDatabase(), getSampleOrderDatabase(), new UserPrefs());
        updatedModel.addCustomer(validCustomer);
        updatedModel.notifyChange(REDONE_COMMAND_TEXT);
        updatedModel.undo();

        assertCommandSuccess(new RedoCommand(), updatedModel, expectedMessage, model);
    }

    @Test
    void execute_noUndo_throwsCommandException() {
        model.addCustomer(validCustomer);
        model.notifyChange(REDONE_COMMAND_TEXT);
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_NOTHING_TO_REDO);
    }
}
