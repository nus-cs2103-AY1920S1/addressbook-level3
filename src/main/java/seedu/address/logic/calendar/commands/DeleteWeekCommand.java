package seedu.address.logic.calendar.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.logic.calendar.commands.exceptions.CommandException;
import seedu.address.model.calendar.CalendarModel;
import seedu.address.model.calendar.task.Task;

/**
 * Deletes all tasks contained in the current week.
 */
public class DeleteWeekCommand extends Command {
    public static final String COMMAND_WORD = "clearweek";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes all tasks of the current week";

    public static final String MESSAGE_DELETE_WEEK_SUCCESS = "All tasks in this week successfully deleted";


    @Override
    public CommandResult execute(CalendarModel calendarModel) throws CommandException {
        requireNonNull(calendarModel);
        ArrayList<Task> deletedTaskList = new ArrayList<>();

        for (Task t: calendarModel.getFilteredTaskList()) {
            if (t.getWeek() == GoCommand.getCurrentWeek()) {
                deletedTaskList.add(t);
            }
        }
        for (Task t:deletedTaskList) {
            calendarModel.deleteTask(t);
        }
        return new CommandResult(MESSAGE_DELETE_WEEK_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }
}
