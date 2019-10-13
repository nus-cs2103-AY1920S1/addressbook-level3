package com.typee.logic.commands.exceptions;

/**
 * Thrown when no previous command to redo.
 */
public class NullRedoableActionException extends CommandException {
    public NullRedoableActionException() {
        super("No command to redo!");
    }
}
