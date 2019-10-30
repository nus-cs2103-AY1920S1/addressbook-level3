package seedu.address.logic.commands.switchcommand;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.model.Model;

/**
 * Command to change the focused panel to Customer
 */
public class SwitchCustomerPanelCommand extends Command {

    public static final String COMMAND_WORD = "switch-c";

    public static final String MESSAGE_SUCCESS = "Switched to Customer panel";


    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, UiChange.CUSTOMER);
    }
}
