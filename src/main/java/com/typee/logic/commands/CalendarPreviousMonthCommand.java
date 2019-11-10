package com.typee.logic.commands;

import com.typee.model.Model;

/**
 * Changes the calendar window's display to the previous month.
 */
public class CalendarPreviousMonthCommand extends CalendarCommand {

    public static final String COMMAND_WORD = "previousmonth";
    public static final String MESSAGE_SUCCESS = "Changing calendar window display to the previous month.";
    public static final String INVALID_COMMAND_FORMAT = "Invalid calendar previous month command format.\n"
            + "Usage: calendar previousmonth";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(MESSAGE_SUCCESS, true, COMMAND_WORD);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else {
            return other instanceof CalendarPreviousMonthCommand;
        }
    }

}
