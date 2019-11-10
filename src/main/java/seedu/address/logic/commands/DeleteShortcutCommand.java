package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.FinSecParser;
import seedu.address.model.Model;
import seedu.address.model.commanditem.CommandItem;

/**
 * Deletes a shortcut specified by the user.
 */
public class DeleteShortcutCommand extends Command {

    public static final String COMMAND_WORD = "delete_shortcut";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the shortcut identified by the shortcut name created previously.\n"
            + "Parameters: SHORTCUT (must be an existing shortcut)\n"
            + "Example: " + COMMAND_WORD + " asdf";

    public static final String MESSAGE_DELETE_SHORTCUT_SUCCESS = "Deleted Shortcut";

    public static final String MESSAGE_DELETE_SHORTCUT_FAIL = "That is a command, not a shortcut.";

    private final CommandItem targetCommand;

    public DeleteShortcutCommand(CommandItem command) {
        this.targetCommand = command;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        try {
            if (!(this.targetCommand.getCommandWord().word == this.targetCommand.getCommandTask().task)) {
                model.deleteCommand(this.targetCommand);
                String commandWordToRemove = this.targetCommand.getCommandWord().word;
                FinSecParser.getCommandList().remove(commandWordToRemove);
                return new CommandResult(String.format(MESSAGE_DELETE_SHORTCUT_SUCCESS));
            } else {
                return new CommandResult(String.format(MESSAGE_DELETE_SHORTCUT_FAIL));
            }
        } catch (Exception e) {
            System.out.println(e);
            return new CommandResult(String.format(MESSAGE_DELETE_SHORTCUT_FAIL));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteShortcutCommand // instanceof handles nulls
                && targetCommand.equals(((DeleteShortcutCommand) other).targetCommand)); // state check
    }


}
