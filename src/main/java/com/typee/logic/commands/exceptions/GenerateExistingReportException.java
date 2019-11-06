package com.typee.logic.commands.exceptions;

/**
 * Throws an exception if the report already exists in the file directory.
 */
public class GenerateExistingReportException extends CommandException {
    public GenerateExistingReportException() {
        super("Report already exists in the system!");
    }
}
