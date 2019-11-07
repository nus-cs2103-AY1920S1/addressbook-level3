package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command!";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format!";
    public static final String MESSAGE_INVALID_COMMAND_PARSER = "CommandParser Error: You should not see this!"
        + " Please open a bug report.";
    public static final String MESSAGE_REQUIRED_COMMAND_ARGUMENT = "Argument '%s' is required!";

    public static final String MESSAGE_INVALID_DATE_TIME = "Invalid date or format!"
        + " Please enter in 'DD/MM/YYYY hh:mm' format.";
    public static final String MESSAGE_INVALID_ICS_DATE_TIME = "Invalid date or format!"
        + " Please enter in 'YYYYMMDDThhmmssZ' format.";
    public static final String MESSAGE_INVALID_CALENDAR_DAY = "Invalid date or format!"
        + " Please enter in 'DD/MM/YYYY' format.";
    public static final String MESSAGE_INVALID_CALENDAR_MONTH = "Invalid month, year or format!"
        + " Please enter in 'MM/YYYY' format.";

    public static final String MESSAGE_INVALID_INDEX = "Invalid index! Please enter an integer. Between [0, 2^31)";
    public static final String MESSAGE_INVALID_EVENT_INDEX = "Invalid index! Event with index '%s' does not exist.";
    public static final String MESSAGE_INVALID_TASK_INDEX = "Invalid index! Task with index '%s' does not exist.";

    public static final String MESSAGE_INVALID_EVENT_END_DATE = "Event 'END_DATE' must be after 'START_DATE'!";
    public static final String MESSAGE_INVALID_EVENT_REMIND_DATE = "Event 'REMIND_DATE' cannot be after 'START_DATE'!";

    public static final String MESSAGE_EXIT_SUCCESS = "Exiting Horo!";

    /* Events & Tasks */
    public static final String MESSAGE_ADD_EVENT_DUPLICATE = "Unable to add event: A duplicate event already exists!";
    public static final String MESSAGE_ADD_EVENT_SUCCESS = "New event added: %s";

    public static final String MESSAGE_ADD_TASK_DUPLICATE = "Unable to add task: A duplicate task already exists!";
    public static final String MESSAGE_ADD_TASK_SUCCESS = "New task added: %s";

    public static final String MESSAGE_DELETE_EVENT_FAILURE = "No matching events.";
    public static final String MESSAGE_DELETE_EVENT_NO_PARAMETERS = "Please fill in at least INDEXES or TAGS.";
    public static final String MESSAGE_DELETE_EVENT_SUCCESS = "Events deleted: %s";

    public static final String MESSAGE_DELETE_TASK_FAILURE = "No matching tasks.";
    public static final String MESSAGE_DELETE_TASK_NO_PARAMETERS = "Please fill in at least INDEXES or TAGS.";
    public static final String MESSAGE_DELETE_TASK_SUCCESS = "Tasks deleted: %s";

    public static final String MESSAGE_EDIT_EVENT_DUPLICATE = "Unable to edit event: A duplicate event already exists!";
    public static final String MESSAGE_EDIT_EVENT_NO_INDEXES = "Please provide at least one INDEX.";
    public static final String MESSAGE_EDIT_EVENT_NO_PARAMETERS = "Please provide at least one parameter.";
    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Events edited: %s";

    public static final String MESSAGE_EDIT_TASK_DUPLICATE = "Unable to edit task: A duplicate task already exists!";
    public static final String MESSAGE_EDIT_TASK_NO_INDEXES = "Please provide at least one INDEX.";
    public static final String MESSAGE_EDIT_TASK_NO_PARAMETERS = "Please provide at least one parameter.";
    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Tasks edited: %s";

    /* Ics */
    public static final String MESSAGE_IMPORT_ICS_SUCCESS = "ICS file at %s has been successfully imported!";
    public static final String MESSAGE_EXPORT_ICS_SUCCESS = "Horo has successfully been exported at the filepath: %s";
    public static final String MESSAGE_IMPORT_ICS_DUPLICATE = "Unable to import:"
        + " A duplicate event or task already exists!";

    /* Notifications */
    public static final String MESSAGE_NOTIFICATION_OFF = "Notifications have been turned off.";
    public static final String MESSAGE_NOTIFICATION_ON = "Notifications have been turned on.";

    /* Ui */
    public static final String MESSAGE_DAY_VIEW_SUCCESS = "Changed Day to: %s";
    public static final String MESSAGE_WEEK_VIEW_SUCCESS = "Changed Week to week of: %s";
    public static final String MESSAGE_MONTH_VIEW_SUCCESS = "Changed Month to: %s";

    public static final String MESSAGE_CALENDAR_VIEW_SUCCESS = "Switched to Calendar View.";
    public static final String MESSAGE_LIST_VIEW_SUCCESS = "Switched to List View.";
    public static final String MESSAGE_LOG_VIEW_SUCCESS = "Switched to Log View";

    /* Undo/Redo */
    public static final String MESSAGE_NOTHING_TO_UNDO = "There is nothing to undo!";
    public static final String MESSAGE_UNDO_SUCCESS = "Previous command has been undone!";
    public static final String MESSAGE_NOTHING_TO_REDO = "There is nothing to redo!";
    public static final String MESSAGE_REDO_SUCCESS = "Previous undone command has been redone!";
}
