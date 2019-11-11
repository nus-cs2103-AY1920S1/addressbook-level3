package seedu.address.calendar.logic.commands;

public class CommandUtil {
    public static final String MESSAGE_DATE_RESTRICTION = "Start date must not be after end date. "
            + "Note that START DAY is compulsory. However, START MONTH and START YEAR are not. "
            + "If they are not specified, the current month and/or year will be used. "
            + "If any fields (day, month or year) of end date are not specified, those from the start date are used.";
}
