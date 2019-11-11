package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.UiManager;
import seedu.address.logic.commands.arguments.CalendarMonthArgument;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.CalendarDate;

//@@author Kyzure
/**
 * Represents a CommandBuilder responsible for creating {@link  CalendarViewCommand}.
 */
class CalendarViewCommandBuilder extends CommandBuilder {

    public static final String OPTION_CALENDAR_SCREEN_DATE = "--date";

    private static final String ARGUMENT_CALENDAR_SCREEN_DATE = "CALENDAR_MONTH_YEAR";

    private UiManager uiManager;
    private CalendarDate calendarDate;

    CalendarViewCommandBuilder(UiManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    protected RequiredArgumentList defineCommandArguments() {
        return null;
    }

    @Override
    protected Map<String, OptionalArgumentList> defineCommandOptions() {
        return Map.of(
                OPTION_CALENDAR_SCREEN_DATE, ArgumentList.optional()
                        .addArgument(CalendarMonthArgument.newBuilder(
                ARGUMENT_CALENDAR_SCREEN_DATE, v -> this.calendarDate = v))
        );
    }

    UiManager getUiManager() {
        return uiManager;
    }

    CalendarDate getCalendarDate() {
        return this.calendarDate;
    }

    @Override
    protected Command commandBuild() {
        return new CalendarViewCommand(this);
    }
}
