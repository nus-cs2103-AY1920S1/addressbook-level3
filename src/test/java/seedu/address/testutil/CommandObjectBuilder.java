package seedu.address.testutil;

import seedu.address.model.commands.CommandAction;
import seedu.address.model.commands.CommandObject;
import seedu.address.model.commands.CommandWord;

/**
 * A utility class to help with building CommandObject objects.
 */
public class CommandObjectBuilder {

    public static final String DEFAULT_COMMAND_WORD = "plus";
    public static final String DEFAULT_COMMAND_ACTION = "add";


    private CommandWord commandWord;
    private CommandAction commandAction;

    public CommandObjectBuilder() {
        commandWord = new CommandWord(DEFAULT_COMMAND_WORD);
        commandAction = new CommandAction(DEFAULT_COMMAND_ACTION);
    }

    /**
     * Initializes the CommandObjectBuilder with the data of {@code commandToCopy}.
     */
    public CommandObjectBuilder(CommandObject commandToCopy) {
        commandWord = commandToCopy.getCommandWord();
        commandAction = commandToCopy.getCommandAction();
    }

    /**
     * Sets the {@code CommandWord} of the {@code CommandObject} that we are building.
     */
    public CommandObjectBuilder withCommandWord(String word) {
        this.commandWord = new CommandWord(word);
        return this;
    }

    /**
     * Sets the {@code CommandAction} of the {@code CommandObject} that we are building.
     */
    public CommandObjectBuilder withCommandAction(String action) {
        this.commandAction = new CommandAction(action);
        return this;
    }

    public CommandObject build() {
        return new CommandObject(commandWord, commandAction);
    }

}
