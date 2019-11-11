package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_ENTITY_DISPLAYED_INDEX = "The entity index provided is invalid";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_NO_FLAG =
            "Please flag the command with '-b' or '-w' to indicate bodies or workers";
    public static final String MESSAGE_INVALID_FLAG = "Invalid flag used! Use -b / -w / -f instead";
    public static final String MESSAGE_BODIES_LISTED_OVERVIEW = "%1$d bodies listed!";
    public static final String MESSAGE_WORKERS_LISTED_OVERVIEW = "%1$d workers listed!";
    public static final String MESSAGE_INVALID_ENTITY_DISPLAYED_ID = "The identification number provided is invalid "
            + "for the selected type of entity.";
    public static final String MESSAGE_INVALID_TEST_PARAMETERS = "Test parameters are invalid";
    public static final String MESSAGE_OCCUPIED_FRIDGE_CANNOT_BE_DELETED = "Fridge is occupied by a body. Please remove"
            + " the body before deleting the fridge";
    public static final String MESSAGE_FRIDGE_DOES_NOT_EXIST = "Fridge specified does not exist!";
    public static final String MESSAGE_INVALID_FRIDGE_ID = "Fridge ID is not valid.";
    public static final String MESSAGE_INEXISTENT_FRIDGE = "Fridge ID does not exist. Please enter an empty fridge's ID"
            + " or create a fridge before adding the body";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format. Please use the following format "
            + "to filter by:\n" + " Week: dd/MM/yyyy (example: stats /week 12/10/2019)\nMonth: MM/yyyy (example stats "
            + "/month 12/2019)\nYear: yyyy (example: stats /year 2019)";
    public static final String MESSAGE_STATS_DEFAULT = "Displaying admissions statistics of the last ten days.";
    public static final String MESSAGE_STATS_WEEK = "Displaying admissions statistics of the week containing "
            + "the day %1$s.";
    public static final String MESSAGE_STATS_MONTH = "Displaying admissions statistics of %1$s.";
    public static final String MESSAGE_STATS_YEAR = "Displaying admissions statistics of the year %1$s.";
    public static final String MESSAGE_INVALID_SIGNATURE_FORMAT = "Invalid signature format! "
            + "Signature should not contain numbers or special characters and must be less than 40 characters.";
    public static final String MESSAGE_INVALD_FRIDE_ID_FORMAT = "Fridge ID needs to be numeric";
    public static final String MESSAGE_DUPLICATE_NOTIF = "This notif already exists in the address book";
    public static final String MESSSAGE_NOTIF_DOES_NOT_EXIST = "Notif does not exist";
    public static final String MESSAGE_BODY_COULD_NOT_BE_UPDATED = "Error updating the status of the body";
    public static final String MESSAGE_DOA_BEFORE_DOD = "The date of admission cannot be before the date of death!";
    public static final String MESSAGE_DOA_BEFORE_DOB = "The date of admission cannot be before the date of birth!";
    public static final String MESSAGE_DOD_BEFORE_DOB = "The date of death cannot be before the date of birth!";
    public static final String MESSAGE_DATEJOINED_BEFORE_DOB = "The date joined cannot be before the date of birth!";
}
