package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";

    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_IRRELEVANT_PREFIXES =
            "Please do not enter irrelevant prefixes/ fields!";
    public static final String MESSAGE_DID_YOU_MEAN_TO_ADD_ANOTHER_PREFIX =
            "Did you mean to add another prefix?";

    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_NO_PERSON_FOUND = "No persons found matching keyword.";
    public static final String MESSAGE_SINGLE_PERSON_LISTED = "1 person listed!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";

    public static final String MESSAGE_INVALID_INCIDENT_INDEX = "The incident index provided is invalid";
    public static final String MESSAGE_INVALID_VEHICLE_INDEX = "The vehicle index provided is invalid";
    public static final String MESSAGE_NO_INCIDENTS_FOUND = "No incidents found matching keyword.";
    public static final String MESSAGE_SINGLE_INCIDENT_LISTED = "1 incident listed!";
    public static final String MESSAGE_INCIDENTS_LISTED_OVERVIEW = "%1$d incidents listed!";

    public static final String MESSAGE_NO_VEHICLES_FOUND = "No vehicles found matching keyword.";
    public static final String MESSAGE_SINGLE_VEHICLE_LISTED = "1 vehicle listed!";
    public static final String MESSAGE_VEHICLES_LISTED_OVERVIEW = "%1$d vehicles listed!";

    public static final String MESSAGE_VEHICLE_ASSIGNMENT_PROMPT =
            "Please include the index of the vehicle you would like to assign, with the district prefix! \n"
                    + "eg new l/1 auto/n v/1 \n"
                    + "Note that the index must be a positive integer!";
    public static final String MESSAGE_VEHICLE_OOB = "Vehicle out of bounds!";
    public static final String MESSAGE_NO_AVAILABLE_VEHICLE = "No vehicle available in this district!";
    public static final String MESSAGE_VEHICLE_BUSY = "Vehicle is busy!";
    public static final String MESSAGE_NO_SUCH_VTYPE = "No such vehicle type!";
    public static final String MESSAGE_INVALID_VEHICLE_NUMBER = "Invalid vehicle number! "
            + "All vehicle numbers must follow the format: ABC1234D";
    public static final String MESSAGE_DUPLICATE_VEHICLE_NUMBER = "A vehicle with this vehicle number already exists, "
            + "please try another.";
    public static final String MESSAGE_INVALID_INDEX_OF_V = "The index must be a positive integer!";

    public static final String MESSAGE_NOT_ONE_DISTRICT =
            "Please ensure there is one input for district number!";

    public static final String MESSAGE_AUTO_ONLY_Y_N = "Auto can only be y or n!";

    public static final String MESSAGE_ALL_INCIDENTS_LISTED = "Listed all incidents";
    public static final String MESSAGE_NO_INCIDENTS_LISTED = "No incident reports present in the system";
    public static final String MESSAGE_ALL_DRAFT_INCIDENTS_LISTED = "Listed all draft incident reports";
    public static final String MESSAGE_NO_DRAFTS_LISTED = "No drafts present in the system";
    public static final String MESSAGE_ALL_COMPLETE_INCIDENTS_LISTED = "Listed all incident reports ready for"
            + " submission";
    public static final String MESSAGE_NO_INCIDENT_TO_SUBMIT = "No reports ready for submission!";
    public static final String MESSAGE_DRAFT_IS_INCOMPLETE = "This draft is incomplete and is not ready for submission"
            + " Please use the 'Fill' command to first complete the report";
    public static final String MESSAGE_INCIDENT_HAS_BEEN_SUBMITTED = "This report has already been submitted.";
    public static final String MESSAGE_REPORT_COMPLETE_BUT_UNFILLED = "Error! Report is marked 'Complete' but is not "
            + "actually fully filled!";
    public static final String MESSAGE_NO_INCIDENT_TO_EDIT = "There are no submitted reports that can be edited";
    public static final String MESSAGE_NO_ACCESS_TO_FILL_DRAFT = "You do not have access to fill this draft as "
            + "another operator has created it.";
    public static final String MESSAGE_NO_ACCESS_TO_SUBMIT_REPORT = "You do not have access to submit this report as "
            + "another operator has created it.";

    public static final String MESSAGE_ALL_SUBMITTED_INCIDENTS_LISTED = "Listed all submitted incident reports "
            + "that can be edited.";

    public static final String MESSAGE_ACCESS_ADMIN = "You must login with an admin account to perform this operation.\n"
            + "Please see help page for more info.";

}
