package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.arguments.DateTimeArgument;
import seedu.address.logic.commands.arguments.DateTimeArgumentBuilder;
import seedu.address.logic.commands.options.Option;
import seedu.address.logic.commands.options.OptionBuilder;
import seedu.address.model.ModelManager;
import seedu.address.model.events.DateTime;

/**
 * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
 */
class DayViewCommandBuilder extends CommandBuilder {

    private static final String ARGUMENT_START_DATE_TIME = "START_DATE_TIME";

    private final ModelManager model;

    private final DateTimeArgumentBuilder start;

    DayViewCommandBuilder(ModelManager model) {
        this.model = model;

        this.start = DateTimeArgument.newBuilder(ARGUMENT_START_DATE_TIME);
    }

    @Override
    OptionBuilder getCommandArguments() {
        return Option.newBuilder()
                .addArgument(this.start);
    }

    @Override
    Map<String, OptionBuilder> getCommandOptions() {
        return Map.of();
    }

    ModelManager getModel() {
        return model;
    }

    DateTime getStart() {
        return this.start.getValue();
    }

    @Override
    Command commandBuild() {
        return new DayViewCommand(this);
    }
}
