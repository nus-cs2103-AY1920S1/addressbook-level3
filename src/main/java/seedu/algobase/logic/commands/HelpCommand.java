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
    public static final String SHOWING_HELP_MESSAGE = "Opened help window.";

    public final boolean isListAllCommands;
    public final Class commandClass;

    public HelpCommand(Class commandClass, boolean isListAllCommands) {
        this.isListAllCommands = isListAllCommands;
        if(!isListAllCommands) {
            requireNonNull(commandClass);
            this.commandClass = commandClass;
        } else {
            this.commandClass = commandClass;
        }
    }

    @Override
    public CommandResult execute(Model model) {
        if(isListAllCommands) {
            List<String> commandWords = new ArrayList<>();
            for(Class command: Command.commandList) {
                commandWords.add(getClassStringField(command, "COMMAND_WORD"));
            }
            return new CommandResult(commandWords.toString(), true, false);
        } else {
            String commandUsage = getClassStringField(commandClass, "MESSAGE_USAGE");
            return new CommandResult(commandUsage, true, false);
        }
    }
}
