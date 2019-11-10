package seedu.sugarmummy.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_CALENDAR_DESCRIPTION;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_CALENDAR_REPETITION;
import static seedu.sugarmummy.logic.parser.CliSyntax.PREFIX_DATETIME;
import static seedu.sugarmummy.model.calendar.CalendarEntry.listToString;

import java.util.List;

import seedu.sugarmummy.logic.commands.Command;
import seedu.sugarmummy.logic.commands.CommandResult;
import seedu.sugarmummy.logic.commands.exceptions.CommandException;
import seedu.sugarmummy.model.Model;
import seedu.sugarmummy.model.calendar.CalendarEntry;
import seedu.sugarmummy.model.calendar.Reminder;
import seedu.sugarmummy.ui.DisplayPaneType;

/**
 * Adds a reminder to the calendar.
 */
public class ReminderCommand extends Command {
    public static final String COMMAND_WORD = "reminder";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a reminder to the calendar. "
            + "Parameters: "
            + PREFIX_CALENDAR_DESCRIPTION + "DESCRIPTION "
            + PREFIX_DATETIME + "DATETIME "
            + "[" + PREFIX_CALENDAR_REPETITION + "REPETITION]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CALENDAR_DESCRIPTION + "Insulin injection "
            + PREFIX_DATETIME + "2020-05-31 16:00 "
            + PREFIX_CALENDAR_REPETITION + "daily";

    public static final String MESSAGE_SUCCESS = "I've successfully added this new reminder: %1$s.";
    public static final String MESSAGE_DUPLICATE_REMINDER = "Oops! This reminder already exists in the calendar.";
    public static final String MESSAGE_SUBSET_REMINDER = "This reminder is covered by %1$s.";
    public static final String MESSAGE_CONFLICTED_REMINDER = "Oops! This reminder and the following reminder(s) "
            + "conflict: ";
    public static final String MESSAGE_SUPERSET_REMINDER = MESSAGE_SUCCESS
            + " The following reminder(s) were removed because they are covered by the new reminder:\n%2$s.";
    private final Reminder toAdd;

    /**
     * Creates an ReminderCommand to add the specified {@code Reminder}
     */
    public ReminderCommand(Reminder reminder) {
        requireNonNull(reminder);
        toAdd = reminder;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasCalendarEntry(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        } else if (model.overlapsCalendarEntry(toAdd)) {
            if (model.conflictsCalendarEntry(toAdd)) {
                throw new CommandException(MESSAGE_CONFLICTED_REMINDER
                        + listToString(model.getCalendarEntryConflicts(toAdd)));
            } else if (model.coversCalendarEntry(toAdd)) {
                throw new CommandException(String.format(MESSAGE_SUBSET_REMINDER, model.getCalendarEntryCovers(toAdd)));
            } else if (model.isAnyCoveredByCalendarEntry(toAdd)) {
                List<CalendarEntry> calendarEntriesToBeDeleted = model.getCalendarEntriesCoveredBy(toAdd);
                String feedBackToUser = String.format(MESSAGE_SUPERSET_REMINDER, toAdd,
                        listToString(calendarEntriesToBeDeleted));
                model.deleteAllCalendarEntries(calendarEntriesToBeDeleted);
                model.addCalendarEntry(toAdd);
                model.schedule();
                return new CommandResult(feedBackToUser);
            }
        }

        model.addCalendarEntry(toAdd);
        model.schedule();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd.toString()));
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
                || (other instanceof ReminderCommand // instanceof handles nulls
                && toAdd.equals(((ReminderCommand) other).toAdd));
    }
}
