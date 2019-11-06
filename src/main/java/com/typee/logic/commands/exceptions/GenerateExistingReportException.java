package com.typee.logic.commands.exceptions;

public class GenerateExistingReportException extends CommandException {
    public GenerateExistingReportException() {
        super("Report already exists in the system!");
    }
}
