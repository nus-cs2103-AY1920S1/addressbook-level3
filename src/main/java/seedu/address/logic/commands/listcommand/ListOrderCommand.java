package seedu.address.logic.commands.listcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_ORDER;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.model.Model;

/**
 * List all customers in the Order book to the user
 */
public class ListOrderCommand extends Command {

    //to be discussed
    public static final String COMMAND_WORD = "list-o";

    public static final String MESSAGE_SUCCESS = "Listed all current orders";


    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        requireNonNull(model);
        model.updateFilteredOrderList(PREDICATE_SHOW_ALL_ORDER);
        return new CommandResult(MESSAGE_SUCCESS, UiChange.ORDER);
    }
}
