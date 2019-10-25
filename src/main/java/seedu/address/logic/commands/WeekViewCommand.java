package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_DAY_VIEW_SUCCESS;

import java.util.Objects;

import seedu.address.logic.UiManager;
import seedu.address.model.CalendarDate;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class WeekViewCommand extends Command {

    private final CalendarDate calendarDate;
    private final UiManager uiManager;

    WeekViewCommand(WeekViewCommandBuilder builder) {
        calendarDate = Objects.requireNonNull(builder.getStart());
        uiManager = Objects.requireNonNull(builder.getUiManager());
    }

    public static CommandBuilder newBuilder(UiManager uiManager) {
        return new WeekViewCommandBuilder(uiManager).init();
    }

    @Override
    public UserOutput execute() {
        uiManager.viewWeek(calendarDate.getDay(), calendarDate.getMonth(), calendarDate.getYear());
        return new UserOutput(String.format(MESSAGE_DAY_VIEW_SUCCESS, calendarDate.toString()));
    }
}
