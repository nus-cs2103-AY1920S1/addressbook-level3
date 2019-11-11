package seedu.deliverymans.logic.commands;

import static seedu.deliverymans.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.deliverymans.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.deliverymans.model.util.SampleDataUtil.getSampleCustomerDatabase;
import static seedu.deliverymans.model.util.SampleDataUtil.getSampleDeliverymenDatabase;
import static seedu.deliverymans.model.util.SampleDataUtil.getSampleOrderDatabase;
import static seedu.deliverymans.model.util.SampleDataUtil.getSampleRestaurantDatabase;
import static seedu.deliverymans.testutil.TypicalIndexes.INDEX_SECOND;

import org.junit.jupiter.api.Test;

import seedu.deliverymans.logic.commands.universal.UndoCommand;
import seedu.deliverymans.model.Model;
import seedu.deliverymans.model.ModelManager;
import seedu.deliverymans.model.UserPrefs;
import seedu.deliverymans.model.customer.Customer;

class UndoCommandTest {
    private static final String UNDONE_COMMAND_TEXT = "delete 2";
    private Model model = new ModelManager(getSampleCustomerDatabase(), getSampleDeliverymenDatabase(),
            getSampleRestaurantDatabase(), getSampleOrderDatabase(), new UserPrefs());

    @Test
    void execute_hasUndo_success() {
        Customer customerToDelete = model.getFilteredCustomerList().get(INDEX_SECOND.getZeroBased());

        String expectedMessage = String.format(UndoCommand.MESSAGE_SUCCESS, UNDONE_COMMAND_TEXT);

        Model updatedModel = new ModelManager(getSampleCustomerDatabase(), getSampleDeliverymenDatabase(),
                getSampleRestaurantDatabase(), getSampleOrderDatabase(), new UserPrefs());
        updatedModel.deleteCustomer(customerToDelete);
        updatedModel.notifyChange(UNDONE_COMMAND_TEXT);

        assertCommandSuccess(new UndoCommand(), updatedModel, expectedMessage, model);
    }

    @Test
    void execute_noUndo_throwsCommandException() {
        assertCommandFailure(new UndoCommand(), model, UndoCommand.MESSAGE_NOTHING_TO_UNDO);
    }
}
