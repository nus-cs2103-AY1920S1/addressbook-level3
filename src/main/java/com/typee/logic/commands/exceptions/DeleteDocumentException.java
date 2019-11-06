package com.typee.logic.commands.exceptions;

public class DeleteDocumentException extends CommandException {
    public DeleteDocumentException() {
        super("Document file to delete does not appear in the directory.");
    }
}
