package seedu.address.logic.commands.switchcommand;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Command to change the focused panel to Schedule
 */
public class SwitchSchedulePanelCommand extends Command {

    public static final String COMMAND_WORD = "switch-s";

    public static final String MESSAGE_SUCCESS = "Switched to Schedule panel";

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory,
                                 UndoRedoStack undoRedoStack) throws CommandException {
        requireNonNull(model);
        model.setCalendarDate(Calendar.getInstance());
        return new CommandResult(MESSAGE_SUCCESS, UiChange.SCHEDULE);
    }
}
