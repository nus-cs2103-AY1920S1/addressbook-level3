package com.typee.logic.commands.exceptions;

public class NullRedoableActionException extends CommandException {
    public NullRedoableActionException() {
        super("No command to redo!");
    }
}