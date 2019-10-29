package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_MONTH_VIEW_SUCCESS;

import java.util.Objects;

import seedu.address.logic.UiManager;
import seedu.address.model.CalendarDate;
import seedu.address.ui.UserOutput;

/**
 * Represents a Command which adds an EventSource to the Model.
 */
public class MonthViewCommand extends Command {

    private final CalendarDate calendarDate;
    private final UiManager uiManager;

    MonthViewCommand(MonthViewCommandBuilder builder) {
        calendarDate = Objects.requireNonNull(builder.getStart());
        uiManager = Objects.requireNonNull(builder.getUiManager());
    }

    public static CommandBuilder newBuilder(UiManager uiManager) {
        return new MonthViewCommandBuilder(uiManager).init();
    }

    @Override
    public UserOutput execute() {
        uiManager.viewMonth(calendarDate);
        return new UserOutput(String.format(MESSAGE_MONTH_VIEW_SUCCESS,
                calendarDate.getEnglishMonth() + " " + calendarDate.getYear()));
    }
}
