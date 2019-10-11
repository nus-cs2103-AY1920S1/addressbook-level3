package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Command to change the focused panel to Schedule
 */
public class SwitchSchedulePanelCommand extends Command {

    public static final String COMMAND_WORD = "switch-s";

    public static final String MESSAGE_SUCCESS = "Switched to Schedule panel";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return new CommandResult(MESSAGE_SUCCESS, UiChange.SCHEDULE);
    }
}
