package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_INDEX;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.Meeting;
import seedu.address.model.calendar.MeetingQuery;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Name;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskStatus;

/**
 * Edits the details of an existing task in the address book.
 */
public class AddMeetingCommand extends Command {

    public static final String COMMAND_WORD = "add-meeting";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a meeting from the list of possible meeting times "
            + "according to the index of the meeting in the list.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_MEETING_INDEX + "MEETING_INDEX\n"
            + "Example: " + COMMAND_WORD + PREFIX_MEETING_INDEX + "1";

    public static final String MESSAGE_SUCCESS = "New meeting added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in your Dashboard";
    public static final String MESSAGE_INVALID_MEETING_REQUEST = "Please first request for a meeting time " +
            "through the find-meeting-time command";

    private final Index index;

    /**
     * @param index of the task in the filtered task list to edit
     */
    public AddMeetingCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        MeetingQuery meetingQuery = model.getMeetingQuery();

        if (meetingQuery == null) {
            throw new CommandException(MESSAGE_INVALID_MEETING_REQUEST);
        }

        List<Meeting> meetingList = meetingQuery.getMeetingList();

        if (index.getZeroBased() >= meetingList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }

        Meeting newMeeting = meetingList.get(index.getZeroBased());

        if (model.hasMeeting(newMeeting)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        }

        model.addMeeting(newMeeting);
        model.updateFilteredMeetingsList(PREDICATE_SHOW_ALL_MEETINGS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, newMeeting));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddMeetingCommand)) {
            return false;
        }

        // state check
        AddMeetingCommand e = (AddMeetingCommand) other;
        return index.equals(e.index);
    }
}
