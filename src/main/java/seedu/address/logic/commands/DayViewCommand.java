package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_DAY_VIEW_SUCCESS;

import java.util.Objects;

import seedu.address.logic.UiManager;
import seedu.address.model.CalendarDate;
import seedu.address.ui.UserOutput;

//@@author Kyzure
/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class DayViewCommand extends Command {

    private final CalendarDate calendarDate;
    private final UiManager uiManager;

    DayViewCommand(DayViewCommandBuilder builder) {
        calendarDate = Objects.requireNonNull(builder.getStart());
        uiManager = Objects.requireNonNull(builder.getUiManager());
    }

    public static CommandBuilder newBuilder(UiManager uiManager) {
        return new DayViewCommandBuilder(uiManager).init();
    }

    @Override
    public UserOutput execute() {
        uiManager.viewDay(calendarDate);
        return new UserOutput(String.format(MESSAGE_DAY_VIEW_SUCCESS, calendarDate.toString()));
    }
}
