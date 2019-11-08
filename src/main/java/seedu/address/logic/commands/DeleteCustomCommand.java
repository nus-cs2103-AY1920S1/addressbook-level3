package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.TutorAidParser;
import seedu.address.model.Model;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.commands.commandsexceptions.CommandNotFoundException;

/**
 * Deletes a custom command previously added by the user.
 */
public class DeleteCustomCommand extends Command {

    public static final String COMMAND_WORD = "deleteCustomCommand";

    public static final String MESSAGE_USAGE = "%1$s: This is not a valid custom command you've added.\n"
            + COMMAND_WORD
            + ": Deletes the custom command previously added\n"
            + "Parameters: COMMAND\n"
            + "Example: " + COMMAND_WORD + " CUSTOM_COMMAND";

    public static final String MESSAGE_DELETE_COMMAND_SUCCESS = "%1$s: Got it we removed the command!";

    public static final String MESSAGE_DELETE_COMMAND_FAIL = "%1$s: You can't remove basic commands!";

    private final CommandObject targetCommand;

    public DeleteCustomCommand(CommandObject targetCommand) {
        this.targetCommand = targetCommand;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        try {
            model.deleteCommand(this.targetCommand);
            String commandWordToRemove = this.targetCommand.getCommandWord().word;
            TutorAidParser.getCommandList().remove(commandWordToRemove);
            return new CommandResult(String.format(MESSAGE_DELETE_COMMAND_SUCCESS, this.targetCommand));
        } catch (CommandNotFoundException e) {
            throw new CommandException(String.format(MESSAGE_DELETE_COMMAND_FAIL, this.targetCommand));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCustomCommand // instanceof handles nulls
                && targetCommand.equals(((DeleteCustomCommand) other).targetCommand)); // state check
    }
}
