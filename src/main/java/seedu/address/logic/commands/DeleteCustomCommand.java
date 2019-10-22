package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.model.Model;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCustomCommand extends Command {

    public static final String COMMAND_WORD = "deleteCustomCommand";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the custom command previously added\n"
            + "Parameters: COMMAND\n"
            + "Example: " + COMMAND_WORD + " CUSTOM_COMMAND";

    public static final String MESSAGE_DELETE_COMMAND_SUCCESS = "Got it we removed the command!";

    public static final String MESSAGE_DELETE_COMMAND_FAIL = "You can't remove basic commands!";

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
            AddressBookParser.getCommandList().remove(commandWordToRemove);
            return new CommandResult(String.format(MESSAGE_DELETE_COMMAND_SUCCESS));
        } catch (Exception e) {
            return new CommandResult(String.format(MESSAGE_DELETE_COMMAND_FAIL));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCustomCommand // instanceof handles nulls
                && targetCommand.equals(((DeleteCustomCommand) other).targetCommand)); // state check
    }
}
