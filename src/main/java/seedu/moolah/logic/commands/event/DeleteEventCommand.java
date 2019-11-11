package seedu.moolah.logic.commands.event;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.moolah.commons.core.Messages;
import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.CommandGroup;
import seedu.moolah.logic.commands.CommandResult;
import seedu.moolah.logic.commands.GenericCommandWord;
import seedu.moolah.logic.commands.UndoableCommand;
import seedu.moolah.logic.commands.exceptions.CommandException;
import seedu.moolah.model.Model;
import seedu.moolah.model.event.Event;
import seedu.moolah.ui.event.EventListPanel;

/**
 * Deletes a expense identified using it's displayed index from the MooLah.
 */
public class DeleteEventCommand extends UndoableCommand {

    public static final String COMMAND_WORD = GenericCommandWord.DELETE + CommandGroup.EVENT;
    public static final String COMMAND_DESCRIPTION = "Delete event with index %1$d";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed event list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Deleted Event:\n %1$s";

    private final Index targetIndex;

    public DeleteEventCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public String getDescription() {
        return String.format(COMMAND_DESCRIPTION, targetIndex.getOneBased());
    }

    @Override
    protected void validate(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }
    }

    @Override
    protected CommandResult execute(Model model) {
        requireNonNull(model);
        List<Event> lastShownList = model.getFilteredEventList();

        Event eventToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteEvent(eventToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_EVENT_SUCCESS, eventToDelete),
                EventListPanel.PANEL_NAME);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteEventCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteEventCommand) other).targetIndex)); // state check
    }
}
