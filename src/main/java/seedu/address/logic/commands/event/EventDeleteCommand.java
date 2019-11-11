package seedu.address.logic.commands.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.EventUtil.vEventToString;

import java.util.List;

import jfxtras.icalendarfx.components.VEvent;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.CommandResultType;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Deletes a event identified using it's displayed index beside it's event name.
 */
public class EventDeleteCommand extends EventCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " delete {index}: deletes the event identified by the index number beside the event name.\n"
            + "Note: {index} must be a positive integer\n"
            + "Example: event delete 3";

    public static final String MESSAGE_DELETE_VEVENT_SUCCESS = "Deleted Event: %1$s";

    private final Index targetIndex;

    public EventDeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<VEvent> lastShownList = model.getVEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        VEvent vEventToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteVEvent(targetIndex);
        return new CommandResult(generateDeleteSuccessMessage(vEventToDelete), CommandResultType.SHOW_SCHEDULE);
    }

    private String generateDeleteSuccessMessage(VEvent vEvent) {
        return String.format(MESSAGE_DELETE_VEVENT_SUCCESS, vEventToString(vEvent));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventDeleteCommand // instanceof handles nulls
                && targetIndex.equals(((EventDeleteCommand) other).targetIndex)); // state check
    }
}
