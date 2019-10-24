package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.commands.arguments.DateTimeArgument;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.ModelManager;
import seedu.address.model.events.DateTime;

/**
 * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
 */
class DayViewCommandBuilder extends CommandBuilder {

    private static final String ARGUMENT_START_DATE_TIME = "START_DATE_TIME";

    private final ModelManager model;

    private DateTime start;

    DayViewCommandBuilder(ModelManager model) {
        this.model = model;
    }

    @Override
    RequiredArgumentList defineCommandArguments() {
        return ArgumentList.required()
            .addArgument(DateTimeArgument.newBuilder(ARGUMENT_START_DATE_TIME, o -> this.start = o));
    }

    @Override
    Map<String, OptionalArgumentList> defineCommandOptions() {
        return null;
    }

    ModelManager getModel() {
        return model;
    }

    DateTime getStart() {
        return this.start;
    }

    @Override
    Command commandBuild() {
        return new DayViewCommand(this);
    }
}
