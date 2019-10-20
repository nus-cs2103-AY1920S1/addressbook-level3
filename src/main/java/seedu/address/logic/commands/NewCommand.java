package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.commands.CommandAction;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.commands.CommandWord;

public class NewCommand extends Command {

    public static final String SHOWING_NEW_COMMAND_MESSAGE = "Got it! We've now mapped ";
    private final String userInput;

    public NewCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public CommandResult execute(Model model) {
        String prevUnknownCommand = model.getSavedCommand();
        CommandObject newCommand = new CommandObject(new CommandWord(prevUnknownCommand),
                new CommandAction(this.userInput));

        model.addCommand(newCommand);

        return new CommandResult(SHOWING_NEW_COMMAND_MESSAGE +
                prevUnknownCommand + " to " + this.userInput,
                false, false, false, false);
    }

}
