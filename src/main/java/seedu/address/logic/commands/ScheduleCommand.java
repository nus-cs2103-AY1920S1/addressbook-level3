package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.Calendar;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Changes the view of the agenda to show the date specified by user.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switch the schedule view according to the date input by the user.\n"
            + "Parameters: PREFIX_CALENDAR + YYYY.MM.DD\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2019.10.18\n";

    public static final String MESSAGE_SUCCESS = "Schedule date switched.";

    private final Calendar calendarToShow;

    public ScheduleCommand(Calendar calendarToShow) {
        this.calendarToShow = calendarToShow;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory commandHistory,
                                 UndoRedoStack undoRedoStack) throws CommandException {
        requireNonNull(model);
        model.setCalendarDate(calendarToShow);
        return new CommandResult(MESSAGE_SUCCESS, UiChange.SCHEDULE);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ScheduleCommand // instanceof handles nulls
                && calendarToShow.equals(((ScheduleCommand) other).calendarToShow));
    }
}
