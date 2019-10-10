package com.typee.logic.commands.exceptions;

/**
 * Thrown when no previous command to undo.
 */
public class NullUndoableActionException extends CommandException {
    public NullUndoableActionException() {
        super("No command to undo!");
    }
}
