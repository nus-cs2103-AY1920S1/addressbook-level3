package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands.
 * Note that prefix definitions can be duplicated so long as they are not used within the same command.
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_PATIENT_VISIT_TODO = new Prefix("vt/");
    //Do not use with PREFIX_PHONE
    public static final Prefix PREFIX_PATIENT_INDEX = new Prefix ("p/");
    //Do not use with PREFIX_ADDRESS
    public static final Prefix PREFIX_APPOINTMENT_INDEX = new Prefix ("a/");
    public static final Prefix PREFIX_VISIT_TASK_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_VISIT_TASK_DETAILS = new Prefix("d/");
    public static final Prefix PREFIX_VISIT_REMARKS = new Prefix("r/");
}
