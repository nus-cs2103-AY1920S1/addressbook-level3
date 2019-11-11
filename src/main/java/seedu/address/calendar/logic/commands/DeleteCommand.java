package seedu.address.calendar.logic.commands;

import seedu.address.calendar.model.Calendar;
import seedu.address.calendar.logic.parser.CliSyntax;
import seedu.address.logic.commands.Command;

public abstract class DeleteCommand extends Command<Calendar> {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_DELETE_SUCCESS = "Deleted: %s";
    public static final String MESSAGE_VALID_TYPES = "The only valid event types are 'commitment', 'holiday', "
            + "'school_break' and 'trip'.";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes the specified event (commitment, holiday, "
            + "school_break, trip) that happens on the specified date(s).\n"
            + "Parameters: "
            + "EVENT TYPE "
            + CliSyntax.PREFIX_START_DAY + "START DAY "
            + "[" + CliSyntax.PREFIX_START_MONTH + "START MONTH] "
            + "[" + CliSyntax.PREFIX_START_YEAR + "START YEAR] "
            + "[" + CliSyntax.PREFIX_END_DAY + "END DAY] "
            + "[" + CliSyntax.PREFIX_END_MONTH + "END MONTH] "
            + "[" + CliSyntax.PREFIX_END_YEAR + "END YEAR] "
            + CliSyntax.PREFIX_NAME + "NAME " + "\n"
            + "Example: " + COMMAND_WORD + " commitment " + CliSyntax.PREFIX_START_DAY + "29 "
            + CliSyntax.PREFIX_START_MONTH + "Nov " + CliSyntax.PREFIX_NAME + "CS2103 exam";

}

