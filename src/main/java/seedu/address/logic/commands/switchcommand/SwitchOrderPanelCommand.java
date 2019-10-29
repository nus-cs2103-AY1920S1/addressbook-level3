package seedu.address.logic.commands.switchcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Command to change the focused panel to Order
 */
public class SwitchOrderPanelCommand extends Command {

    public static final String COMMAND_WORD = "switch-o";

    public static final String MESSAGE_SUCCESS = "Switched to Order panel";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory,
                                 UndoRedoStack undoRedoStack) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, UiChange.ORDER);
    }
}
