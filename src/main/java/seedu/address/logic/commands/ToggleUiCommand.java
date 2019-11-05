package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_UNUSED_ARGUMENT;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class ToggleUiCommand extends Command {

    public static final String COMMAND_WORD = "toggleui";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Toggles between Light and Dark Themes \n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_SUCCESS = "UI Theme Changed";

    private String unusedArguments = null;

    public ToggleUiCommand() {}

    public ToggleUiCommand(String unusedArguments) {
        if (!unusedArguments.equals("")) {
            this.unusedArguments = unusedArguments;
        }
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {

        if (unusedArguments != null) {
            return new CommandResult(String.format(MESSAGE_SUCCESS
                    + MESSAGE_UNUSED_ARGUMENT, unusedArguments, COMMAND_WORD),
                    false, false, false, false, true);
        } else {
            return new CommandResult(MESSAGE_SUCCESS, false, false, false, false, true);
        }
    }

    @Override
    public boolean equals(Object other) {
        return (other == this || other instanceof ToggleUiCommand);
    }
}
