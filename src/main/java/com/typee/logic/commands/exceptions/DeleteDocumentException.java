package com.typee.logic.commands.exceptions;

/**
 * Exception if document for deletion does not appear in the directory
 */
public class DeleteDocumentException extends CommandException {
    public DeleteDocumentException() {
        super("Document file to delete does not appear in the directory.");
    }
}
