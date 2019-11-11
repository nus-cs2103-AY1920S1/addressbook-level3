package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_CALENDAR_VIEW_SUCCESS;

import java.util.Objects;

import seedu.address.logic.UiManager;
import seedu.address.model.CalendarDate;
import seedu.address.ui.UserOutput;

//@@author Kyzure
/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class CalendarViewCommand extends Command {

    private final UiManager uiManager;
    private final CalendarDate calendarDate;

    CalendarViewCommand(CalendarViewCommandBuilder builder) {
        this.uiManager = Objects.requireNonNull(builder.getUiManager());
        this.calendarDate = builder.getCalendarDate();
    }

    public static CommandBuilder newBuilder(UiManager uiManager) {
        return new CalendarViewCommandBuilder(uiManager).init();
    }

    @Override
    public UserOutput execute() {
        uiManager.viewCalendar(this.calendarDate);
        return new UserOutput(String.format(MESSAGE_CALENDAR_VIEW_SUCCESS));
    }
}
