package seedu.address.logic.calendar.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.calendar.CalendarModel;

/**
 * Switch the calendar to display tasks of the specified week.
 */
public class GoCommand extends Command {
    public static final String COMMAND_WORD = "go";
    public static final String MESSAGE_SUCCESS = "Calendar switched to week %1$s";

    private static int currentWeek = 0;

    public GoCommand(int targetIndex) {
        currentWeek = targetIndex;
    }

    public static int getCurrentWeek() {
        return currentWeek;
    }


    @Override
    public CommandResult execute(CalendarModel calendarModel) {
        requireNonNull(calendarModel);
        calendarModel.updateLists();
        return new CommandResult(String.format(MESSAGE_SUCCESS, currentWeek));
    }
}
