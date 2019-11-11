package mams.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX = "The student index provided is invalid";
    public static final String MESSAGE_INVALID_STUDENT_MATRIC_ID = "The student matric ID provided is invalid";
    public static final String MESSAGE_STUDENTS_LISTED_OVERVIEW = "%1$d student(s) listed!";
    public static final String MESSAGE_MODULES_LISTED_OVERVIEW = "%1$d module(s) listed!";
    public static final String MESSAGE_APPEALS_LISTED_OVERVIEW = "%1$d appeal(s) listed!";
    public static final String MESSAGE_INVALID_APPEAL_DISPLAYED_INDEX = "The appeal index provided is invalid";
    public static final String MESSAGE_INVALID_MODULE_DISPLAYED_INDEX = "The module index provided is invalid";
    public static final String MESSAGE_INVALID_CREDIT_VALUE = "The value for credits is invalid";
    public static final String MESSAGE_CREDIT_UNDER_AMT_MODS = "The new credit score is below amount "
            + "required for current modules taken";
    public static final String MESSAGE_CREDIT_INSUFFICIENT = "Unable to add module to student"
            + " as it will exceed student's credit limit.";
    public static final String MESSAGE_STUDENT_COMPLETED_MODULE = "Unable to add module to student"
            + " as the student already has already completed the module ";

}
