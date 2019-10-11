package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_PATIENT_VISIT_TODO = new Prefix("vt/");
    public static final Prefix PREFIX_PATIENT = new Prefix ("p/");
    public static final Prefix PREFIX_APPOINTMENT = new Prefix ("a/");
    public static final Prefix PREFIX_VISIT_TASK_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_VISIT_TASK_DETAILS = new Prefix("d/");
    public static final Prefix PREFIX_VISIT_REMARKS = new Prefix("r/");
}
