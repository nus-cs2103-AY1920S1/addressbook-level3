package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_MONTH_VIEW_SUCCESS;

import java.util.Map;
import java.util.Objects;

import seedu.address.logic.UiManager;
import seedu.address.logic.commands.arguments.CalendarMonthArgument;
import seedu.address.logic.commands.arguments.list.ArgumentList;
import seedu.address.logic.commands.arguments.list.OptionalArgumentList;
import seedu.address.logic.commands.arguments.list.RequiredArgumentList;
import seedu.address.model.CalendarDate;
import seedu.address.ui.UserOutput;

//@@author Kyzure
/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class MonthViewCommand extends Command {

    private final CalendarDate calendarDate;
    private final UiManager uiManager;

    private MonthViewCommand(Builder builder) {
        calendarDate = Objects.requireNonNull(builder.calendarDate);
        uiManager = Objects.requireNonNull(builder.uiManager);
    }

    public static CommandBuilder newBuilder(UiManager uiManager) {
        return new Builder(uiManager).init();
    }

    @Override
    public UserOutput execute() {
        uiManager.viewMonth(calendarDate);
        return new UserOutput(String.format(MESSAGE_MONTH_VIEW_SUCCESS, calendarDate));
    }

    /**
     * Represents a CommandBuilder responsible for creating {@link AddEventCommand}.
     */
    private static class Builder extends CommandBuilder {

        private static final String ARGUMENT_MONTH = "MONTH_YEAR";

        private CalendarDate calendarDate;
        private UiManager uiManager;

        private Builder(UiManager uiManager) {
            this.uiManager = uiManager;
        }

        @Override
        protected RequiredArgumentList defineCommandArguments() {
            return ArgumentList.required()
                .addArgument(CalendarMonthArgument.newBuilder(ARGUMENT_MONTH, o -> this.calendarDate = o));
        }

        @Override
        protected Map<String, OptionalArgumentList> defineCommandOptions() {
            return null;
        }

        @Override
        protected Command commandBuild() {
            return new MonthViewCommand(this);
        }
    }
}
