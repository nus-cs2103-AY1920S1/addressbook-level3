package seedu.pluswork.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for task */
    public static final Prefix PREFIX_TASK_NAME = new Prefix("tn/");
    public static final Prefix PREFIX_TASK_INDEX = new Prefix("ti/");
    public static final Prefix PREFIX_TASK_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_TASK_TAG = new Prefix("tt/");
    public static final Prefix PREFIX_DEADLINE = new Prefix("at/");

    /* Prefix definitions for Member */
    public static final Prefix PREFIX_MEMBER_NAME = new Prefix("mn/");
    public static final Prefix PREFIX_MEMBER_ID = new Prefix("mi/");
    public static final Prefix PREFIX_MEMBER_TAG = new Prefix("mt/");
    public static final Prefix PREFIX_MEMBER_IMAGE = new Prefix("im/");

    /*Prefix definitions for inventory*/
    public static final Prefix PREFIX_INVENTORY_NAME = new Prefix("i/");
    public static final Prefix PREFIX_INVENTORY_PRICE = new Prefix("p/");
    public static final Prefix PREFIX_INVENTORY_INDEX = new Prefix("ii/");
    public static final Prefix PREFIX_INVENTORY_PDFTYPE = new Prefix("ty/");

    /*Prefix definitions for calendar*/
    public static final Prefix PREFIX_FILE_PATH = new Prefix("fp/");

    /*Prefix definitions for meeting*/
    public static final Prefix PREFIX_MEETING_INDEX = new Prefix("meeting/");
    public static final Prefix PREFIX_START_PERIOD = new Prefix("start/");
    public static final Prefix PREFIX_END_PERIOD = new Prefix("end/");
    public static final Prefix PREFIX_DURATION_HOURS = new Prefix("hours/");
}
