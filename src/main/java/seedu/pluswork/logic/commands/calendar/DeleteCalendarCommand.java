package seedu.pluswork.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_MEMBER_NAME;

import java.util.List;

import seedu.pluswork.commons.core.Messages;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.calendar.CalendarWrapper;
import seedu.pluswork.model.member.MemberName;

/**
 * Deletes a task identified using it's displayed index from the address book.
 */
public class DeleteCalendarCommand extends Command {

    public static final String COMMAND_WORD = "delete-calendar";
    public static final String PREFIX_USAGE = PREFIX_MEMBER_NAME.getPrefix();

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the calendar identified by the member name used when adding the calendar.\n"
            + "Parameters:" + PREFIX_MEMBER_NAME + "MEMBER_NAME\n"
            + "Example: " + COMMAND_WORD + PREFIX_MEMBER_NAME + "Gabriel";

    public static final String MESSAGE_SUCCESS = "Deleted Calendar: %1$s";

    private final MemberName calendarMemberName;

    public DeleteCalendarCommand(MemberName calendarMemberName) {
        requireNonNull(calendarMemberName);
        this.calendarMemberName = calendarMemberName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<CalendarWrapper> calendarWrapperList = model.getFilteredCalendarList();

        for (CalendarWrapper calendar : calendarWrapperList) {
            if (calendar.hasMemberName(calendarMemberName)) {
                model.deleteCalendar(calendar);
                return new CommandResult(String.format(MESSAGE_SUCCESS, calendar));
            }
        }
        throw new CommandException(Messages.MESSAGE_INVALID_CALENDAR_MEMBER_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCalendarCommand // instanceof handles nulls
                && calendarMemberName.equals(((DeleteCalendarCommand) other).calendarMemberName)); // state check
    }
}
