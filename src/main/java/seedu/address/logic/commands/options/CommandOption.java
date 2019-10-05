package seedu.address.logic.commands.options;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.arguments.CommandArgument;

/**
 * Represents an option of a Command.
 * An option is always optional, and can contain other arguments.
 */
public class CommandOption {

    private final List<CommandArgument> arguments;

    public CommandOption() {
        this.arguments = new ArrayList<>();
    }

    /**
     * Adds an argument to this option.
     * @param argument the argument
     * @return this instance
     */
    public CommandOption addCommandArgument(CommandArgument argument) {
        this.arguments.add(argument);
        return this;
    }

    public List<CommandArgument> getArguments() {
        return this.arguments;
    }
}
