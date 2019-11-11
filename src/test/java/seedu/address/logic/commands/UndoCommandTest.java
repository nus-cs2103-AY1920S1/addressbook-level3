package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCustomers.getTypicalCustomerBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
import static seedu.address.testutil.TypicalOrders.getTypicalOrderBook;
import static seedu.address.testutil.TypicalPhones.getTypicalPhoneBook;
import static seedu.address.testutil.TypicalSchedules.getTypicalScheduleBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.deletecommand.DeleteCustomerCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.order.Order;

public class UndoCommandTest {
    private static final UndoRedoStack EMPTY_STACK = new UndoRedoStack();

    @Test
    public void execute_emptyUndoRedoStack_failure() {
        Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(), getTypicalOrderBook(),
                getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(), getTypicalOrderBook(),
                getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());

        assertThrows(CommandException.class, UndoCommand.MESSAGE_FAILURE, () -> new UndoCommand().execute(model,
                new CommandHistory(), EMPTY_STACK));

    }

    @Test
    public void execute_nonEmptyUndoRedoStack_success() throws CommandException {
        Model model = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(), getTypicalOrderBook(),
                getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        Model expectedModel = new ModelManager(getTypicalCustomerBook(), getTypicalPhoneBook(), getTypicalOrderBook(),
                getTypicalScheduleBook(), new DataBook<Order>(), new UserPrefs());
        UndoRedoStack undoRedoStack = new UndoRedoStack();



        UndoableCommand deleteCommand = new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER);
        CommandResult commandResult = deleteCommand.execute(model, new CommandHistory(), undoRedoStack);
        deleteCommand.saveSuccessMessage(commandResult.getFeedbackToUser());
        undoRedoStack.push(deleteCommand);

        assertCommandSuccess(new UndoCommand(), model, undoRedoStack,
                String.format(UndoCommand.MESSAGE_SUCCESS, commandResult.getFeedbackToUser()), expectedModel);

    }

}
