package seedu.address.logic.commands.clearcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * Clears the schedule book.
 */
public class ClearScheduleCommand extends Command {

    public static final String COMMAND_WORD = "clear-s";
    public static final String MESSAGE_SUCCESS = "Schedule book has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        List<Schedule> schedule = model.getScheduleBook().getList();
        for (int i = schedule.size() - 1; i >= 0; i--) {
            model.deleteSchedule(schedule.get(i));
        }

        return new CommandResult(MESSAGE_SUCCESS, UiChange.SCHEDULE);
    }
}
