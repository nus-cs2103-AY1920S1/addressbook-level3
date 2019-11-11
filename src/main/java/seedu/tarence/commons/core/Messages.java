package seedu.tarence.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_ASSIGNMENT_IN_TUTORIAL = "No such assignment in this tutorial!";
    public static final String MESSAGE_INVALID_ASSIGNMENT_DISPLAYED_INDEX = "The assignment index provided is invalid";
    public static final String MESSAGE_INVALID_EVENT_IN_TUTORIAL = "No such event in this tutorial!";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_FILE = "Invalid file";
    public static final String MESSAGE_INVALID_INDEX_NON_POSITIVE = "The index(es) provided must be greater than 0.";
    public static final String MESSAGE_INVALID_INDEX_BEYOND_RANGE = "The index provided is invalid (exceeds range).";
    public static final String MESSAGE_INVALID_MODULE_DISPLAYED_INDEX = "The module index provided is invalid.";
    public static final String MESSAGE_INVALID_MODULE_IN_APPLICATION = "No module of this code exists!";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_STUDENT_IN_TUTORIAL = "No such student in this tutorial!";
    public static final String MESSAGE_INVALID_TUTORIAL_DISPLAYED_INDEX = "The tutorial index provided is invalid";
    public static final String MESSAGE_INVALID_TUTORIAL_IN_APPLICATION = "No tutorial of this name exists!";
    public static final String MESSAGE_INVALID_TUTORIAL_INDEX_FORMAT =
            "Tutorial index should only be a positive integer.";
    public static final String MESSAGE_INVALID_TUTORIAL_IN_MODULE = "No such tutorial in this module!";
    public static final String MESSAGE_INVALID_TUTORIAL_MULTIPLE = "Multiple tutorials of the same name exist!\n"
            + "Please specify the module code.";
    public static final String MESSAGE_INVALID_WEEK_IN_TUTORIAL = "No such week in this tutorial!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_SUGGESTED_CORRECTIONS = "%1$s %2$s not found! Did you mean one of the following:"
            + "\n(Enter a number to select that option and re-run the command)\n";
    public static final String MESSAGE_MULTIPLE_OF_SAME_NAME = "Multiple students of name '%1$s' found! Please select "
            + "from the following:\n";
    public static final String MESSAGE_INVALID_TAB = "No such tab exists!";
    public static final String MESSAGE_INVALID_DISPLAY_FORMAT = "No such format to display!";
    public static final String MESSAGE_INVALID_SEMESTER_START_DATE = "Entered start date is the same as the previous "
            + "date %s";
}
