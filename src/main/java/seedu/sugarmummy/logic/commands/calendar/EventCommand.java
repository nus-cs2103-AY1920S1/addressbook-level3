package seedu.sugarmummy.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_CALENDAR_DESCRIPTION;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_TIME_DURATION;
import static seedu.sugarmummy.model.calendar.CalendarEntry.listToString;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.calendar.Event;
import seedu.sugarmummy.model.calendar.exceptions.DuplicateCalendarEntryException;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Adds an Event to the calendar.
 */
public class EventCommand extends Command {
    public static final String COMMAND_WORD = "event";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an event to the calendar. "
            + "Parameters: "
            + PREFIX_CALENDAR_DESCRIPTION + "DESCRIPTION "
            + PREFIX_DATETIME + "DATETIME "
            + "[" + PREFIX_DATETIME + "ENDING DATETIME] "
            + "[" + PREFIX_TIME_DURATION + "TIME DURATION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CALENDAR_DESCRIPTION + "Appointment "
            + PREFIX_DATETIME + "2020-05-04 09:00 "
            + PREFIX_DATETIME + "2020-05-04 11:00 "
            + PREFIX_TIME_DURATION + "00:30";

    public static final String MESSAGE_SUCCESS = "New event added: %1$s";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the calendar";
    public static final String MESSAGE_SUCCESS_WITH_OVERLAP = MESSAGE_SUCCESS
            + "\nHowever, it overlaps with the following events: %2$s";
    public static final String MESSAGE_SUCCESS_WITH_REMINDER = "\nWith auto reminder added: %2$s";
    public static final String MESSAGE_SUCCESS_REMINDER_EXISTED = "Reminder already existed";

    private final Event toAdd;

    /**
     * Creates an EventCommand to add the specified {@code Event}
     */
    public EventCommand(Event event) {
        requireNonNull(event);
        toAdd = event;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCalendarEntry(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        String feedbackToUser = MESSAGE_SUCCESS;
        if (model.overlapsCalendarEntry(toAdd)) {
            feedbackToUser = String.format(MESSAGE_SUCCESS_WITH_OVERLAP,
                    toAdd, listToString(model.getCalendarEntryOverlaps(toAdd)));
        }
        model.addCalendarEntry(toAdd);
        if (toAdd.getAutoReminder().isPresent()) {
            try {
                model.addCalendarEntry(toAdd.getAutoReminder().get());
                model.schedule();
                feedbackToUser += String.format(MESSAGE_SUCCESS_WITH_REMINDER, toAdd, toAdd.getAutoReminder().get());
            } catch (DuplicateCalendarEntryException e) {
                feedbackToUser += MESSAGE_SUCCESS_REMINDER_EXISTED;
            }
        }
        return new CommandResult(feedbackToUser);
    }

    @Override
    public DisplayPaneType getDisplayPaneType() {
        return DisplayPaneType.CALENDAR_ENTRY;
    }

    @Override
    public boolean isToCreateNewPane() {
        return true;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventCommand // instanceof handles nulls
                && toAdd.equals(((EventCommand) other).toAdd));
    }
}
