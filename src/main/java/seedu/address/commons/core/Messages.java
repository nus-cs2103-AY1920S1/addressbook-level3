package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_EMPLOYEE_DISPLAYED_INDEX = "The employee index provided is invalid";
    public static final String MESSAGE_INVALID_EMPLOYEE_PAID = "The stated amount payment amount is invalid.";
    public static final String MESSAGE_INVALID_EVENT_DISPLAYED_INDEX = "The event index provided is invalid";
    public static final String MESSAGE_DATE_NOT_FOUND_IN_MAP =
            "The Date [%s] is not found in the Date-Time Mapping of Event: [%s]";
    public static final String MESSAGE_EMPLOYEE_LISTED_OVERVIEW = "%1$d employee listed!";
    public static final String MESSAGE_EMPLOYEES_LISTED_OVERVIEW = "%1$d employees listed!";
    public static final String MESSAGE_EVENT_LISTED_OVERVIEW = "%1$d event listed!";
    public static final String MESSAGE_EVENTS_LISTED_OVERVIEW = "%1$d events listed!";
    public static final String MESSAGE_GENERATE_SUCCESS = "Schedule Generated";
    public static final String MESSAGE_GENERATE_FAILURE = "There is no events available, unable to generate schedule";
    public static final String MESSAGE_UNAVAILABLE_MANPOWER = "Employee to allocate is unavailable for this event!";
    public static final String MESSAGE_MANPOWER_COUNT_EXCEEDED = "Manpower count to allocate "
            + "exceeds manpower needed by event!";
    public static final String MESSAGE_INSUFFICIENT_MANPOWER_COUNT = "Insufficient manpower count for event! "
            + "(either due to schedule overlap or lack of manpower)";
    public static final String MESSAGE_EVENT_FULL_MANPOWER = "Event has full manpower!";
    public static final String MESSAGE_EVENT_INVALID_EMPLOYEE_ID = "Invalid Employee ID!";
    public static final String MESSAGE_DATE_INVALID = "Invalid date: %s \n"
            + "Date should be in the following format dd/MM/yyyy, "
            + "be a valid Calendar Date, and be in the last 10 years.";
    public static final String MESSAGE_DATE_TOO_OLD = "Only dates of the last 10 years is accepted";
    public static final String MESSAGE_DATE_BIG_RANGE =
            "The maximum acceptable range between the Start and End Dates is 90 days. \n"
            + "The number of days between your stated Start Date [%s] and your End Date [%s] is %s days";
    public static final String MESSAGE_EMPLOYEE_ALREADY_ALLOCATED = "Employee to allocate is already "
            + "allocated to the event!";
    public static final String MESSAGE_EMPLOYEE_DOES_NOT_EXIST = "Employee to free is currently"
            + " not allocated to event!";
    public static final String MESSAGE_WRONG_WINDOW = "Command should be executed in the Main Window since it "
            + "requires references to the Employee list.";
}
