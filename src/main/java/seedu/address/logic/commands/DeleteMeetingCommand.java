package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MEETING_INDEX;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.calendar.Meeting;

/**
 * Deletes a task identified using it's displayed index from the address book.
 */
public class DeleteMeetingCommand extends Command {

    public static final String COMMAND_WORD = "delete-meeting";
    public static final String PREFIX_USAGE = PREFIX_MEETING_INDEX.toString();

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the meeting identified by the index in the meeting list.\n"
            + "Parameters:" + PREFIX_MEETING_INDEX + "MEETING_INDEX\n"
            + "Example: " + COMMAND_WORD + PREFIX_MEETING_INDEX + "1";

    public static final String MESSAGE_DELETE_MEETING_SUCCESS = "Deleted Meeting: %1$s";

    private final Index meetingIndex;

    public DeleteMeetingCommand(Index meetingIndex) {
        requireNonNull(meetingIndex);
        this.meetingIndex = meetingIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Meeting> meetingList = model.getFilteredMeetingList();

        if (meetingIndex.getZeroBased() <= meetingList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }
        Meeting meetingToDelete = meetingList.get(meetingIndex.getZeroBased());

        model.deleteMeeting(meetingToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_MEETING_SUCCESS, meetingToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteMeetingCommand // instanceof handles nulls
                && meetingIndex.equals(((DeleteMeetingCommand) other).meetingIndex)); // state check
    }
}
