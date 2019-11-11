//@@author wongsm7
package seedu.address.logic.commands.queue;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.index.Index;

import seedu.address.logic.commands.common.CommandResult;
import seedu.address.logic.commands.common.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.Model;
import seedu.address.model.ReferenceId;
import seedu.address.model.queue.Room;

/**
 * Undo the next command.
 */
public class UndoNextCommand extends ReversibleCommand {
    public static final String COMMAND_WORD = "next";
    public static final String MESSAGE_SUCCESS = "Next patient has been unallocated from room ";
    public static final String MESSAGE_DUPLICATE_ROOM = "This room already exists in the list.";

    private final Room roomToEdit;
    private final Room editedRoom;
    private final ReferenceId patientReferenceId;
    private final Index index;

    public UndoNextCommand(Room roomToEdit, Room editedRoom, Index index, ReferenceId patientReferenceId) {
        this.editedRoom = editedRoom;
        this.roomToEdit = roomToEdit;
        this.index = index;
        this.patientReferenceId = patientReferenceId;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.isPatientInQueue(patientReferenceId)) {
            throw new CommandException(MESSAGE_DUPLICATE_ROOM);
        }

        model.enqueuePatientToIndex(patientReferenceId, 0);
        model.removeRoom(roomToEdit);

        if (model.hasRoom(editedRoom)) {
            throw new CommandException(MESSAGE_DUPLICATE_ROOM);
        }

        model.addRoom(editedRoom);
        return new CommandResult(MESSAGE_SUCCESS + editedRoom);
    }

}
