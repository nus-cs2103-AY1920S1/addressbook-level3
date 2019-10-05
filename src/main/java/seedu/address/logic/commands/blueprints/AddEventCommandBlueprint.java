package seedu.address.logic.commands.blueprints;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandOption;
import seedu.address.logic.commands.arguments.CommandArgument;
import seedu.address.logic.commands.arguments.DateTimeArgument;
import seedu.address.logic.commands.arguments.StringArgument;

public class AddEventCommandBlueprint extends CommandBuilder {

    private static final String OPTION_END = "--end";

    private final StringArgument description;

    public AddEventCommandBlueprint() {
        this.description = new StringArgument();
    }

    @Override
    public List<CommandArgument> getCommandArguments() {
        return List.of(this.description);
    }

    @Override
    public Map<String, CommandOption> getCommandOptions() {
        return Map.of(OPTION_END, new CommandOption().addCommandArgument(new DateTimeArgument()));
    }

    @Override
    public Command build() {
        return new ;
    }
}
