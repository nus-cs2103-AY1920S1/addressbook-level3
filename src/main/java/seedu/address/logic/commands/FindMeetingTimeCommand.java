package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import seedu.address.model.Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.List;

/**
 * Lists all persons in the address book to the user.
 */
public class FindMeetingTimeCommand extends Command {

    public static final String COMMAND_WORD = "find-meeting-time";

    public static final String MESSAGE_SUCCESS = "Found a meeting time";

    private final LocalDateTime startDate;
    private final LocalDateTime endDate;
    private final Duration meetingDuration;

    /**
     * Creates an AddCommand to add the specified {@code Task}
     */
    public FindMeetingTimeCommand(LocalDateTime startDate, LocalDateTime endDate, Duration meetingDuration) {
        //Check if non null
        this.startDate = startDate;
        this.endDate = endDate;
        this.meetingDuration = meetingDuration;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        //Replace after including meeting display into UI
        List<LocalDateTime> possibleMeetingTimes = model.findMeetingTime(startDate, endDate, meetingDuration);
        String LIST_OF_TIMINGS = "The following are good meeting times: \n";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (LocalDateTime dateTime : possibleMeetingTimes) {
            LIST_OF_TIMINGS += dateTime.format(formatter) + "\n";
        }
        return new CommandResult(LIST_OF_TIMINGS);
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
