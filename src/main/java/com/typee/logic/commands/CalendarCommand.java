package com.typee.logic.commands;

/**
 * Represents a command which interacts with the calendar window.
 */
public abstract class CalendarCommand extends Command {

    public static final String COMMAND_WORD = "calendar";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Performs the specified operation to the calendar window.\n"
            + "Parameters: OPERATION OPERATION_ARGUMENTS\n"
            + "Open display example: " + COMMAND_WORD + " opendisplay 10/11/2019\n"
            + "Next month example: " + COMMAND_WORD + " nextmonth\n"
            + "Previous month example: " + COMMAND_WORD + " previousmonth\n";
}
