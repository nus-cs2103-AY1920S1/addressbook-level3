package seedu.address.logic.calendar.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_TASKTITLE = new Prefix("<title>");
    public static final Prefix PREFIX_TASKDAY = new Prefix("<day>");
    public static final Prefix PREFIX_TASKDESCRIPTION = new Prefix("<desc>");
    public static final Prefix PREFIX_TASKDEADLINE = new Prefix("<deadline>");
    public static final Prefix PREFIX_TASKTIME = new Prefix("<time>");
    public static final Prefix PREFIX_TASKTAG = new Prefix("<tag>");

}
