package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.UiManager;
import seedu.address.logic.commands.arguments.CalendarDayArgument;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.CalendarDate;

//@@author Kyzure
/**
 * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
 */
class DayViewCommandBuilder extends CommandBuilder {

    private static final String ARGUMENT_START_DATE_TIME = "DATE";

    private CalendarDate start;
    private UiManager uiManager;

    DayViewCommandBuilder(UiManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    protected RequiredArgumentList defineCommandArguments() {
        return ArgumentList.required()
            .addArgument(CalendarDayArgument.newBuilder(ARGUMENT_START_DATE_TIME, o -> this.start = o));
    }

    @Override
    protected Map<String, OptionalArgumentList> defineCommandOptions() {
        return null;
    }

    UiManager getUiManager() {
        return uiManager;
    }

    CalendarDate getStart() {
        return this.start;
    }

    @Override
    protected Command commandBuild() {
        return new DayViewCommand(this);
    }
}
