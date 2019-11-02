package seedu.address.logic.commands.clearcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.UiChange;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.model.Model;
import seedu.address.model.schedule.Schedule;

/**
 * Clears the schedule book.
 */
public class ClearScheduleCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear-s";
    public static final String MESSAGE_SUCCESS = "Schedule book has been cleared!";

    @Override
    public CommandResult executeUndoableCommand(Model model, CommandHistory commandHistory,
                                                UndoRedoStack undoRedoStack) {
        requireNonNull(model);

        List<Schedule> schedule = model.getScheduleBook().getList();
        for (int i = schedule.size() - 1; i >= 0; i--) {
            model.deleteSchedule(schedule.get(i));
        }

        return new CommandResult(MESSAGE_SUCCESS, UiChange.SCHEDULE);
    }
}
