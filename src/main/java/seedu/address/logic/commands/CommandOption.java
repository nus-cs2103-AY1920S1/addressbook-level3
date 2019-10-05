package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.arguments.CommandArgument;

public class CommandOption {

    private final boolean optional;
    private final List<CommandArgument> arguments;

    public CommandOption() {
        this.optional = false;
        this.arguments = new ArrayList<>();
    }

    public CommandOption addCommandArgument(CommandArgument argument) {
        this.arguments.add(argument);
        return this;
    }
}
