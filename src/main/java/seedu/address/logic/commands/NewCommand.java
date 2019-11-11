package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.commands.CommandAction;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.commands.CommandWord;


/**
 * Adds a new command to the TutorAid's commands list.
 */
public class NewCommand extends Command {

    public static final String SHOWING_NEW_COMMAND_MESSAGE = "Got it! You can now use %1$s as ";
    private final String commandAction;
    private final String prevUnknownCommand;

    public NewCommand(String commandAction, String prevUnknownCommand) {
        this.commandAction = commandAction;
        this.prevUnknownCommand = prevUnknownCommand;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        CommandWord newCommandWord = new CommandWord(this.prevUnknownCommand);
        CommandAction newCommandAction = new CommandAction(this.commandAction);
        CommandObject newCommand = new CommandObject(newCommandWord, newCommandAction);

        model.addCommand(newCommand);

        return new CommandResult(String.format(SHOWING_NEW_COMMAND_MESSAGE + this.commandAction,
                this.prevUnknownCommand));

    }

}
