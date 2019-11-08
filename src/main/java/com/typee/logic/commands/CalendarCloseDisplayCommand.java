package com.typee.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;

import com.typee.commons.util.DateUtil;
import com.typee.model.Model;

/**
 * Closes the engagements list for a particular date in the calendar window.
 */
public class CalendarCloseDisplayCommand extends CalendarCommand {

    public static final String COMMAND_WORD = "closedisplay";
    public static final String MESSAGE_SUCCESS = "Closing display for ";
    public static final String INVALID_COMMAND_FORMAT = "Invalid calendar close display command format.\n"
            + "Usage: calendar opendisplay DD/MM/YYYY\n";

    private LocalDate date;

    public CalendarCloseDisplayCommand(LocalDate date) {
        requireNonNull(date);
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) {
        String formattedDateString = DateUtil.getFormattedDateString(date);
        return new CommandResult(MESSAGE_SUCCESS + formattedDateString, true, date, COMMAND_WORD);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof CalendarCloseDisplayCommand)) {
            return false;
        } else {
            CalendarCloseDisplayCommand otherCalendarCloseDisplayCommand =
                    (CalendarCloseDisplayCommand) other;
            return this.date.equals(otherCalendarCloseDisplayCommand.date);
        }
    }

}
