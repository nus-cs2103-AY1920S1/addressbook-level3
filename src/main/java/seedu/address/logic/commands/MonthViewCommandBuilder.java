package seedu.address.logic.commands;

import java.util.Map;

import seedu.address.logic.UiManager;
import seedu.address.logic.commands.arguments.MonthYearArgument;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.CalendarDate;

/**
 * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
 */
class MonthViewCommandBuilder extends CommandBuilder {

    private static final String ARGUMENT_MONTH = "MONTH_YEAR";

    private CalendarDate start;
    private UiManager uiManager;

    MonthViewCommandBuilder(UiManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    RequiredArgumentList defineCommandArguments() {
        return ArgumentList.required()
                .addArgument(MonthYearArgument.newBuilder(ARGUMENT_MONTH, o -> this.start = o));
    }

    @Override
    Map<String, OptionalArgumentList> defineCommandOptions() {
        return null;
    }

    UiManager getUiManager() {
        return uiManager;
    }

    CalendarDate getStart() {
        return this.start;
    }

    @Override
    Command commandBuild() {
        return new MonthViewCommand(this);
    }
}
