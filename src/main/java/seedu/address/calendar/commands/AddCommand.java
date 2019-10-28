package seedu.address.calendar.commands;

import seedu.address.calendar.parser.CliSyntax;

public abstract class AddCommand extends Command {
    public static final String COMMAND_WORD = "add";
    public static final String MESSAGE_ADD_SUCCESS = "Added: %s";
    // todo: how to use month and year shown by calendar instead? Possible: pass in this as arg when calling parser
    public static final String MESSAGE_DATE_RESTRICTION = "Start date must not be after end date. "
            + "Note that START DAY is compulsory. However, START MONTH and START YEAR are not. "
            + "If they are not specified, the current month and/or year will be used. "
            + "If any fields (day, month or year) of end date are not specified, those from the start date are used.";
    public static final String MESSAGE_VALID_TYPES = "The only valid event types are 'commitment', 'holiday', "
            + "'school_break' and 'trip'.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the specified event (commitment, holiday, "
            + "school_break, trip) to the specified date(s)."
            + "Parameters: "
            + "EVENT TYPE"
            + CliSyntax.PREFIX_START_DAY + " START DAY "
            + "[" + CliSyntax.PREFIX_START_MONTH + " START MONTH] "
            + "[" + CliSyntax.PREFIX_START_YEAR + "START YEAR] "
            + "[" + CliSyntax.PREFIX_END_DAY + " END DAY] "
            + "[" + CliSyntax.PREFIX_END_MONTH + " END MONTH] "
            + "[" + CliSyntax.PREFIX_END_YEAR + " END YEAR] "
            + CliSyntax.PREFIX_NAME + " NAME "
            + "[" + CliSyntax.PREFIX_INFO + " INFO]" + "\n"
            + "Example: " + COMMAND_WORD + " commitment " + CliSyntax.PREFIX_START_DAY + " 29 "
            + CliSyntax.PREFIX_START_MONTH + " Nov " + CliSyntax.PREFIX_NAME + " CS2103 exam";
}
