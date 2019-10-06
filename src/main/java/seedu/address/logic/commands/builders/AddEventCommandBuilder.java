package seedu.address.logic.commands.builders;

import java.util.Map;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.builders.arguments.DateTimeArgument;
import seedu.address.logic.commands.builders.arguments.StringArgument;
import seedu.address.logic.commands.builders.options.Option;
import seedu.address.model.events.DateTime;

/**
 * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
 */
public class AddEventCommandBuilder extends CommandBuilder {

    private static final String ARGUMENT_DESCRIPTION = "DESCRIPTION";
    private static final String ARGUMENT_START_DATE_TIME = "START_DATE";
    private static final String ARGUMENT_END_DATE_TIME = "END_DATE";

    private static final String OPTION_END = "--end";

    private String description;
    private DateTime start;
    private DateTime end;

    @Override
    Option getCommandArguments() {
        return new Option()
            .addArgument(new StringArgument(ARGUMENT_DESCRIPTION, d -> this.description = d))
            .addArgument(new DateTimeArgument(ARGUMENT_START_DATE_TIME, s -> this.start = s));
    }

    @Override
    Map<String, Option> getCommandOptions() {
        return Map.of(
            OPTION_END, new Option()
                .addArgument(new DateTimeArgument(ARGUMENT_END_DATE_TIME, e -> this.end = e))
        );
    }

    @Override
    Command buildCommand() {
        return new AddEventCommand(this.description, this.start);
    }
}
