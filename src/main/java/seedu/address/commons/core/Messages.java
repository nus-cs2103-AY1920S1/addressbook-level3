package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_COMMAND_DOES_NOT_EXIST = "Command does not exist";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_INVALID_CUSTOMER_DISPLAYED_INDEX = "The customer index provided is invalid";
    public static final String MESSAGE_CUSTOMERS_LISTED_OVERVIEW = "%1$d customers listed!";
    public static final String MESSAGE_INVALID_PHONE_DISPLAYED_INDEX = "The phone index provided is invalid";
    public static final String MESSAGE_INVALID_ORDER_DISPLAYED_INDEX = "The order index provided is invalid";
    public static final String MESSAGE_PHONE_LISTED_OVERVIEW = "%1$d phones listed!";
    public static final String MESSAGE_ORDERS_LISTED_OVERVIEW = "%1$d orders listed!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_ORDER_SCHEDULED = "This order is already scheduled in SML.";
    public static final String MESSAGE_ORDER_CANCELLED = "This order is already cancelled in SML.";
    public static final String MESSAGE_ORDER_COMPLETED = "This order is already completed in SML.";
    public static final String MESSAGE_ORDER_UNSCHEDULED = "This order is unscheduled in SML.";
    public static final String MESSAGE_SCHEDULE_CONFLICT = "Scheduling conflict detected. Add \"-allow\" flag to "
            + "confirm overlapping schedules.";

    public static final String TIME_MESSAGE_CONSTRAINTS = "Time should only contain numbers in the format of HH.MM, "
            + "with valid hour and minute.";
    public static final String DATE_MESSAGE_CONSTRAINTS_LESS_THAN_DEFAULT = "Year should not be less than 1970.";
    public static final String DATE_MESSAGE_CONSTRAINTS_LESS_THAN_0 =
            "Date should only contain numbers in the format of YYYY.MM.DD and be positive";
    public static final String DATE_MESSAGE_CONSTRAINTS =
            "Date should only contain numbers in the format of YYYY.MM.DD, with valid year, month and date.";
    public static final String OPTIONAL_DATE_MESSAGE_CONSTRAINTS =
            "Date should have starting and ending in the format YYYY.MM.DD\n"
                    + "Example: d1/2018.10.05 d2/2019.04.12";
    public static final String STATS_MESSAGE_CONSTRAINTS =
            "Stat type should only be either profit, cost or revenue, and should not be blank.";
    public static final String STATS_MESSAGE_CONSTRAINTS_NO_INVALID_CHAR = "Invalid input!\n"
                    + "Either enter the default mode: generate-s s/{stat type} "
                    + "or the date input mode: generate-s s/{stat type d1/yyyy.mm.dd d2/yyyy.mm.dd";
    public static final String MESSAGE_INVALID_DATE_INPUT_FORMAT =
            "Starting date should be lesser than that of ending date";
}
