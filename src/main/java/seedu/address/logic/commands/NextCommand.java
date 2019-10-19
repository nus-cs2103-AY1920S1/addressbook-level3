package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.queue.Room;

/**
 * Serves the next patient in queue.
 */
public class NextCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_SUCCESS = "Next patient has been allocated to room ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Allocates next patient in queue to a room. "
            + "Parameters: INDEX (must be a positive integer)";

    private final Index targetIndex;

    public NextCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Room> lastShownList = model.getConsultationRoomList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_ROOM_INDEX);
        }
        model.serveNextPatient(targetIndex.getOneBased());
        return new CommandResult(MESSAGE_SUCCESS + targetIndex);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NextCommand // instanceof handles nulls
                && targetIndex.equals(((NextCommand) other).targetIndex)); // state check
    }
}
