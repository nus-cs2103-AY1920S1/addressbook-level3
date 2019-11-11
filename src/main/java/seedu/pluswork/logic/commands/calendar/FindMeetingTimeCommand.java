package seedu.pluswork.logic.commands.calendar;

import static java.util.Objects.requireNonNull;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_DURATION_HOURS;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_END_PERIOD;
import static seedu.pluswork.logic.parser.CliSyntax.PREFIX_START_PERIOD;

import java.time.Duration;
import java.time.LocalDateTime;

import seedu.pluswork.commons.util.DateTimeUtil;
import seedu.pluswork.logic.commands.Command;
import seedu.pluswork.logic.commands.CommandResult;
import seedu.pluswork.logic.commands.exceptions.CommandException;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.calendar.MeetingQuery;

/**
 * Lists all persons in the address book to the user.
 */
public class FindMeetingTimeCommand extends Command {

    public static final String COMMAND_WORD = "find-meeting-time";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Generates a list of possible meeting times between the dates specified and " +
            "of the specified duration.\n"
            + "Parameters:" + PREFIX_START_PERIOD + "dd/mm/yyyy hh:mm" + PREFIX_END_PERIOD + "dd/mm/yyyy hh:mm"
            + PREFIX_DURATION_HOURS + "DURATION_IN_HOURS\n"
            + "Example: " + COMMAND_WORD + PREFIX_START_PERIOD + "25/10/2019 08:00" + PREFIX_END_PERIOD
            + "26/10/2019 17:00" + PREFIX_DURATION_HOURS + "3";

    public static final String PREFIX_USAGE = "start/ end/ hours/";

    public static final String MESSAGE_SUCCESS = "Found a meeting time between %1$s - %2$s";
    public static final String MESSAGE_FAILURE = "Could not find a meeting time between %1$s - %2$s";

    public static final String ILLEGAL_END_DATE = "Please enter an end time that is after the start time";

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Duration meetingDuration;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public FindMeetingTimeCommand(LocalDateTime startDate, LocalDateTime endDate, Duration meetingDuration) {
        requireNonNull(startDate);
        requireNonNull(endDate);
        requireNonNull(meetingDuration);
        this.startDate = startDate;
        this.endDate = endDate;
        this.meetingDuration = meetingDuration;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (endDate.isBefore(startDate)) {
            throw new CommandException(ILLEGAL_END_DATE);
        }

        model.findMeetingTime(startDate, endDate, meetingDuration);
        MeetingQuery meetingQuery = model.getMeetingQuery();
        assert (meetingQuery != null);
        if (!meetingQuery.hasMeetings()) {
            String startDateString = DateTimeUtil.displayDateTime(startDate);
            String endDateString = DateTimeUtil.displayDateTime(endDate);
            return new CommandResult(String.format(MESSAGE_FAILURE, startDateString, endDateString));
        } else {
            String startDateString = DateTimeUtil.displayDateTime(startDate);
            String endDateString = DateTimeUtil.displayDateTime(endDate);
            return new CommandResult(String.format(MESSAGE_SUCCESS, startDateString, endDateString));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindMeetingTimeCommand // instanceof handles nulls
                && startDate.equals(((FindMeetingTimeCommand) other).startDate)
                && endDate.equals(((FindMeetingTimeCommand) other).endDate)
                && meetingDuration.equals(((FindMeetingTimeCommand) other).meetingDuration));
    }
}
