package seedu.address.logic.commands;

import seedu.address.model.Model;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.commanditem.CommandTask;
import seedu.address.model.commanditem.CommandWord;

/**
 * Represents the command that the user wants to create a shortcut. The user input will be the existing command /
 * functionality that he wants to map the previously inputted value as the shortcut to.
 */
public class CreateShortCutCommand extends Command {

    public static final String SHOWING_NEW_SHORTCUT_MESSAGE = "Success! New shortcut created. ";
    private final String currentInput;
    private final String prevInput;

    public CreateShortCutCommand(String currentInput, String previousInput) {
        this.currentInput = currentInput;
        this.prevInput = previousInput;
    }

    public String getCurrentInput() {
        return currentInput;
    }

    public String getPrevInput() {
        return prevInput;
    }

    @Override
    public CommandResult execute(Model model) {
        CommandItem newCommand = new CommandItem(new CommandWord(this.prevInput),
                new CommandTask(this.currentInput));

        model.addCommand(newCommand);

        return new CommandResult(SHOWING_NEW_SHORTCUT_MESSAGE
                + this.prevInput + " to " + this.currentInput,
                false, false, false, false, false);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateShortCutCommand);
    }
}
