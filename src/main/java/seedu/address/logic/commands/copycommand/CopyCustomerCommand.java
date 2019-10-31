package seedu.address.logic.commands.copycommand;

import static java.util.Objects.requireNonNull;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.customer.Customer;

/**
 * Copies a customer identified using it's displayed index from the seller manager.
 */
public class CopyCustomerCommand extends Command {

    public static final String COMMAND_WORD = "copy-c";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Copies the customer identified by the index number used in the displayed customer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_COPY_CUSTOMER_SUCCESS = "Copied customer into clipboard: %1$s";

    private final Index targetIndex;

    public CopyCustomerCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory,
                                 UndoRedoStack undoRedoStack) throws CommandException {
        requireNonNull(model);
        List<Customer> lastShownList = model.getFilteredCustomerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX);
        }

        Customer customerToCopy = lastShownList.get(targetIndex.getZeroBased());

        String customerString = customerToCopy.toString();
        StringSelection stringSelection = new StringSelection(customerString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);


        return new CommandResult(String.format(MESSAGE_COPY_CUSTOMER_SUCCESS, customerToCopy), UiChange.CUSTOMER);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CopyCustomerCommand // instanceof handles nulls
                && targetIndex.equals(((CopyCustomerCommand) other).targetIndex)); // state check
    }
}
