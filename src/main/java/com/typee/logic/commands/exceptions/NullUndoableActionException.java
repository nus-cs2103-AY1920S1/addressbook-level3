package com.typee.logic.commands.exceptions;

public class NullUndoableActionException extends CommandException {
    public NullUndoableActionException() {
        super("No command to undo!");
    }
}