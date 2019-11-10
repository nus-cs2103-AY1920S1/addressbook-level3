package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Prints out the current {@code UserSettings}.
 */
public class ViewSettingsCommand extends Command {
    public static final String MESSAGE_SET_USER_SETTINGS_SUCCESS = "Set User Settings:\n%1$s";

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        return new CommandResult(String.format(MESSAGE_SET_USER_SETTINGS_SUCCESS, model.getUserSettings()));
    }

    @Override
    public boolean equals(Object other) {
        return (other == this || other instanceof ViewSettingsCommand);
    }
}
