package seedu.address.logic.commands;

/**
 * Marker Interface for commands. This interface is used to mark a command if the command's execution will
 * mutate the state of the data in Model and should have its state tracked by ModelHistory. This is to facilitate
 * the tracking of state for the undo/redo features.
 */
public interface TrackableState {
}
