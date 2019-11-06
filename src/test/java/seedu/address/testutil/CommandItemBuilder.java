package seedu.address.testutil;

import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.commanditem.CommandTask;
import seedu.address.model.commanditem.CommandWord;

/**
 * Helps with building of commanditem object
 */
public class CommandItemBuilder {

    public static final String DEFAULT_COMMANDWORD = "add";
    public static final String DEFAULT_COMMANDTASK = "add_contact";

    private CommandWord commandWord;
    private CommandTask commandTask;


    public CommandItemBuilder() {
        commandWord = new CommandWord(DEFAULT_COMMANDWORD);
        commandTask = new CommandTask(DEFAULT_COMMANDTASK);
    }

    /**
     * Initializes the CommandItemBuilder with the data of {@code incomeToCopy}.
     */
    public CommandItemBuilder(CommandItem commandToCopy) {
        commandWord = commandToCopy.getCommandWord();
        commandTask = commandToCopy.getCommandTask();
    }

    /**
     * Sets the {@code commandWord} of the {@code FinSec} that we are building.
     */
    public CommandItemBuilder withCommandWord(String commandWord) {
        this.commandWord = new CommandWord(commandWord);
        return this;
    }

    /**
     * Sets the {@code commandTask} of the {@code FinSec} that we are building.
     */
    public CommandItemBuilder withCommandTask(String commandTask) {
        this.commandTask = new CommandTask(commandTask);
        return this;
    }

    public CommandItem build() {
        return new CommandItem(commandWord, commandTask);
    }
}
