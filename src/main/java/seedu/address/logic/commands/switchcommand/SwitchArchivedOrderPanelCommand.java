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
 * Command to change the focused panel to Archived Order
 */
public class SwitchArchivedOrderPanelCommand extends Command {

    public static final String COMMAND_WORD = "switch-a";

    public static final String MESSAGE_SUCCESS = "Switched to archived Order panel";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory,
                                 UndoRedoStack undoRedoStack) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, UiChange.ARCHIVED_ORDER);
    }
}
