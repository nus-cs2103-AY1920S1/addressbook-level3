package seedu.address.logic.commands.clearcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Clears the customer book.
 */
public class ClearCustomerCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear-c";
    public static final String MESSAGE_SUCCESS = "Customer book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand(Model model, CommandHistory commandHistory,
                                                UndoRedoStack undoRedoStack) {
        requireNonNull(model);
        model.setCustomerBook(new DataBook<Customer>());
        return new CommandResult(MESSAGE_SUCCESS, UiChange.CUSTOMER);
    }
}
