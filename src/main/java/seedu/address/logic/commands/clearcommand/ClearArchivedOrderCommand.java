package seedu.address.logic.commands.clearcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.DataBook;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

/**
 * Clears the archived order book.
 */
public class ClearArchivedOrderCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear-a";
    public static final String MESSAGE_SUCCESS = "Archived order book has been cleared!";


    @Override
    public CommandResult executeUndoableCommand(Model model, CommandHistory commandHistory,
                                                UndoRedoStack undoRedoStack) {
        requireNonNull(model);
        model.setArchivedOrderBook(new DataBook<Order>());
        return new CommandResult(MESSAGE_SUCCESS, UiChange.ARCHIVED_ORDER);
    }
}
