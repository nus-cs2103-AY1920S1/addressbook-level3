package seedu.algobase.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.getClassStringField;

import java.util.ArrayList;
import java.util.List;

import seedu.algobase.model.Model;

/**
 * Format full help instructions for every command for display.
 */
public class HelpCommand extends Command {

    public static final String COMMAND_WORD = "help";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Shows program usage instructions.\n"
        + "Parameter:\n"
        + "COMMAND_NAME (can be empty if you want a list of possible commands)\n"
        + "Example: " + COMMAND_WORD + "find";

    private final boolean isListAllCommands;
    private final Class commandClass;

    public HelpCommand(Class commandClass, boolean isListAllCommands) {
        this.isListAllCommands = isListAllCommands;
        if (!isListAllCommands) {
            requireNonNull(commandClass);
            this.commandClass = commandClass;
        } else {
            this.commandClass = commandClass;
        }
    }

    public boolean isListAllCommands() {
        return isListAllCommands;
    }

    public Class getCommandClass() {
        return commandClass;
    }

    @Override
    public CommandResult execute(Model model) {
        if (isListAllCommands) {
            List<String> commandWords = new ArrayList<>();
            for (Class command : Command.COMMAND_LIST) {
                commandWords.add(getClassStringField(command, "COMMAND_WORD"));
            }
            String commandPrompt = "Available commands are: " + commandWords.toString() + "\n"
                + "More information can be found in the popup window.";
            System.out.println(commandPrompt);
            return new CommandResult(commandPrompt, true, false);
        } else {
            String commandUsage = getClassStringField(commandClass, "MESSAGE_USAGE");
            return new CommandResult(commandUsage, false, false);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof HelpCommand)) {
            return false;
        }

        // state check
        HelpCommand h = (HelpCommand) other;
        return isListAllCommands == h.isListAllCommands()
            && commandClass.equals(h.getCommandClass());
    }
}
