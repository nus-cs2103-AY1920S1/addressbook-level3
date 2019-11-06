package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.commands.CommandAction;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.commands.CommandWord;

/**
 * Adds a new command to the TutorAid's commands list.
 */
public class NewCommand extends Command {

    public static final String SHOWING_NEW_COMMAND_MESSAGE = "Got it! You can now use ";
    private final String userInput;
    private final String prevUnknownCommand;

    public NewCommand(String userInput, String prevUnknownCommand) {
        this.userInput = userInput;
        this.prevUnknownCommand = prevUnknownCommand;
    }

    @Override
    public CommandResult execute(Model model) {
        CommandObject newCommand = new CommandObject(new CommandWord(this.prevUnknownCommand),
                new CommandAction(this.userInput));

        model.addCommand(newCommand);

        return new CommandResult(SHOWING_NEW_COMMAND_MESSAGE
                + this.prevUnknownCommand + " as " + this.userInput);

    }

}
