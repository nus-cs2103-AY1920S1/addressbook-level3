package seedu.address.logic.commands.blueprints;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandOption;
import seedu.address.logic.commands.arguments.CommandArgument;

public abstract class CommandBuilder {

    private CommandOption context;
    private List<CommandArgument> arguments;
    private Map<String, CommandOption> options;

    public CommandBuilder() {
        this.arguments = getCommandArguments();
        this.options = getCommandOptions();
    }

    public void addArgument(String argument) {
        if (context == null) {
            this.arguments.add(argument);
        } else {
            this.context.add(argument);
        }
    }

    public boolean containsOption(String option) {
        return this.options.containsKey(option);
    }

    public void setContext(String option) {
        this.context = this.options.get(option);
    }

    public abstract List<CommandArgument> getCommandArguments();

    public abstract Map<String, CommandOption> getCommandOptions();

    public abstract Command build();
}
