package seedu.address.logic.commands.builders;

import java.util.List;
import java.util.Map;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.arguments.CommandArgument;
import seedu.address.logic.commands.arguments.DateTimeArgument;
import seedu.address.logic.commands.arguments.StringArgument;
import seedu.address.logic.commands.exceptions.ArgumentException;
import seedu.address.logic.commands.options.CommandOption;

/**
 * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
 */
public class AddEventCommandBuilder extends CommandBuilder {

    private static final String ARGUMENT_DESCRIPTION = "DESCRIPTION";
    private static final String ARGUMENT_START_DATE_TIME = "START_DATE";
    private static final String ARGUMENT_END_DATE_TIME = "END_DATE";

    private static final String OPTION_END = "--end";

    private final StringArgument description;
    private final DateTimeArgument start;
    private final DateTimeArgument end;

    public AddEventCommandBuilder() {
        this.description = new StringArgument(ARGUMENT_DESCRIPTION, true);
        this.start = new DateTimeArgument(ARGUMENT_START_DATE_TIME, true);
        this.end = new DateTimeArgument(ARGUMENT_END_DATE_TIME, false);
    }

    @Override
    public List<CommandArgument> getCommandArguments() {
        return List.of(this.description, this.start);
    }

    @Override
    public Map<String, CommandOption> getCommandOptions() {
        return Map.of(OPTION_END, new CommandOption().addCommandArgument(this.end));
    }

    @Override
    public Command build() throws ArgumentException {
        return new AddEventCommand(this.description.getValue(), this.start.getValue());
    }
}
