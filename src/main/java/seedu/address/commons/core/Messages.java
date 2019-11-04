package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";

    public static final String MESSAGE_DUPLICATE_QUESTION = "This question already exist.";
    public static final String MESSAGE_QUESTION_NOT_FOUND = "Unable to find question.";
    public static final String MESSAGE_EMPTY_QUESTION_DISPLAYED_INDEX = "Please provide a question index.";
    public static final String MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX = "The question index provided is invalid.";
    public static final String MESSAGE_MISSING_QUESTION_OPTIONS = "Options A to D are necessary for mcq questions.";
    public static final String MESSAGE_MISSING_QUESTION_OPTIONS_VALUE = "%s cannot be left empty.";
    public static final String MESSAGE_INVALID_QUESTION_TYPE = "Please enter a valid question type.";
    public static final String MESSAGE_MISSING_QUESTION = "Question cannot be left empty.";
    public static final String MESSAGE_MISSING_ANSWER = "Answer cannot be left empty.";
    public static final String MESSAGE_MISSING_TYPE = "Type cannot be left empty.";
    public static final String MESSAGE_MISSING_TEXT_SEARCH = "Please provide a text to search.";
    public static final String MESSAGE_MISSING_EDIT_FIELDS = "At least one field to edit has to be defined.";
    public static final String MESSAGE_MISSING_INPUT_FIELDS = "1 or more input fields are left empty.";

    public static final String MESSAGE_INVALID_STUDENT_DISPLAYED_INDEX = "The student index provided is invalid";

    public static final String MESSAGE_INVALID_NOTE_PREAMBLE = "Note index must be a positive integer immediately"
            + " preceded by valid fields to edit. \n%1$s";
    public static final String MESSAGE_INVALID_NOTE_DISPLAYED_INDEX = "The note index provided is invalid.";
    public static final String MESSAGE_DUPLICATE_NOTE = "This note already exists in the notes record";

    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";

    public static final String MESSAGE_SAVE_STATS_FILE_ERROR = "Statistics Command Error. "
            + "Printable file name field cannot be empty or have any special characters \\ / : * ? \" < > |";
    public static final String EXCEL_FILE_NOT_FOUND = "Excel file was not found. Please ensure file path is valid.";
    public static final String EXCEL_FILE_NOT_PARSED = "Error occurred retrieving file. Please try with another file";
    public static final String EXCEL_FILE_TYPE_ISSUE = "File type must be excel workbook with extension '.xlsx'."
            + " Please try again.";
    public static final String EXCEL_FILE_ILLEGAL_INPUT = "File has illegal input. Please refer to user guide.";
    public static final String EXCEL_FILE_ILLEGAL_FORMAT = "File has illegal format. Please refer to user guide.";
    public static final String EXCEL_ILLEGAL_HEADER = "Cell A1 should be 'Students'";

}
