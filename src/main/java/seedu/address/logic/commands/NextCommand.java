package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.common.ReferenceId;
import seedu.address.model.queue.Room;

import java.util.List;

/**
 * Serves the next patient in queue.
 */
public class NextCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_SUCCESS = "Next patient has been allocated to room ";
    public static final String MESSAGE_UNDO_NEXT_SUCCESS = "Allocation has been undone";
    public static final String MESSAGE_UNDO_NEXT_ERROR = "Allocation cannot be undone!";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allocates next patient in queue to a room. "
            + "Parameters: INDEX (must be a positive integer)";

    private final Index targetIndex;

    public NextCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Room> lastShownList = model.getFilteredRoomList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ROOM_INDEX);
        }
        model.serveNextPatient(targetIndex.getOneBased());
        return new CommandResult(MESSAGE_SUCCESS + targetIndex);
    }

    @Override
    public CommandResult undo(Model model) throws CommandException {
        requireNonNull(model);
        List<Room> lastShownList = model.getFilteredRoomList();
        ReferenceId referenceId = lastShownList.get(targetIndex.getZeroBased()).getCurrentPatient().get();

        if (referenceId == null || !model.hasPerson(referenceId) || model.isPatientInQueue(referenceId)) {
            throw new CommandException(MESSAGE_UNDO_NEXT_ERROR);
        }
        model.enqueuePatientToFront(referenceId);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_UNDO_NEXT_SUCCESS);
    }
}
