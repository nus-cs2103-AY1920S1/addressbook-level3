package cs.f10.t1.nursetraverse.autocomplete;

/**
 * Contains autocomplete word guide description
 */
class WordDescriptionBank {
    // ObjectWord description
    static final String PAT_DESCRIPTION =
            "Select this to manage patient data.";
    static final String VISIT_DESCRIPTION =
            "Select this when you are on a visit.";
    static final String APP_DESCRIPTION =
            "Select this to follow up with general commands that deals with NurseTraverse as a whole.";
    static final String APPT_DESCRIPTION =
            "Select this to manage appointments.";

    // CommandWord description
    static final String BEGIN_VISIT_DESCRIPTION =
            "Select this command to start a visit";
    static final String CANCEL_VISIT_DESCRIPTION =
            "Select this command to cancel current visit";
    static final String FINISH_VISIT_DESCRIPTION =
            "Select this command to finish current visit";
    static final String UPDATE_VISIT_DESCRIPTION =
            "Select this command to update current visit";

    static final String UNDO_DESCRIPTION =
            "Select this command to undo previous command entered";
    static final String REDO_DESCRIPTION =
            "Select this command to redo previous command that was undid";
    static final String IMPORT_REPLACE_DESCRIPTION =
            "Select this command to import data and replace the current data";
    static final String IMPORT_MERGE_DESCRIPTION =
            "Select this command to import data and merge with the current data";
    static final String EXPORT_DESCRIPTION =
            "Select this command to export data";
    static final String HELP_DESCRIPTION =
            "Select this command to for the link to our user guide";
    static final String EXIT_DESCRIPTION =
            "Select this command to exit NurseTraverse";

    static final String LIST_DESCRIPTION =
            "Select this command to list all patients";
    static final String FIND_DESCRIPTION =
            "Select this command to find a specific patient by entering keywords";
    static final String ADD_DESCRIPTION =
            "Select this command to add a new patient";
    static final String EDIT_DESCRIPTION =
            "Select this command to edit the details of a specified patient";
    static final String DELETE_DESCRIPTION =
            "Select this command to delete a patient";
    static final String CLEAR_DESCRIPTION =
            "Select this command to empty all patient data";

    static final String FIND_APPT_DESCRIPTION =
            "Select this command to find a specific patient appointment by entering keywords";
    static final String ADD_APPT_DESCRIPTION =
            "Select this command to add a new appointment";
    static final String EDIT_APPT_DESCRIPTION =
            "Select this command to edit a specified appointment";
    static final String DELETE_APPT_DESCRIPTION =
            "Select this command to delete an appointment";


    // IndexWord description
    static final String INDEX_P_DESCRIPTION =
            "Select patient index to continue.";
    static final String INDEX_A_DESCRIPTION =
            "Select appointment index to continue.";

    // PrefixWord description
    static final String PREFIX_NAME_DESCRIPTION =
            "This is the syntax for patient name.";
    static final String PREFIX_PHONE_DESCRIPTION =
            "This is the syntax for patient phone number.";
    static final String PREFIX_EMAIL_DESCRIPTION =
            "This is the syntax for patient email address.";
    static final String PREFIX_ADDRESS_DESCRIPTION =
            "This is the syntax for patient address.";
    static final String PREFIX_TAG_DESCRIPTION =
            "This is the syntax for patient tag.";

    static final String PREFIX_PATIENT_VISIT_TODO_DESCRIPTION =
            "This is the syntax for the things to do the next time you visit the patient.";

    static final String PREFIX_PATIENT_INDEX_DESCRIPTION =
            "This is the syntax for patient index.";
    static final String PREFIX_VISIT_TASK_INDEX_AND_DETAIL_DESCRIPTION =
            "This is the syntax for visit task index and detail.";
    static final String PREFIX_VISIT_TASK_FINISH_DESCRIPTION =
            "This is the syntax for visit task index that needs to be updated as finished.";
    static final String PREFIX_VISIT_TASK_UNFINISH_DESCRIPTION =
            "This is the syntax for visit task index that needs to be updated as unfinished.";
    static final String PREFIX_VISIT_REMARKS_DESCRIPTION =
            "This is the syntax for visit remarks.";

    static final String PREFIX_FILENAME_DESCRIPTION =
            "This is the syntax for file name.";
    static final String PREFIX_INDEXES_DESCRIPTION =
            "This is the syntax for indexes.";

    static final String PREFIX_APPT_START_DATE_AND_TIME_DESCRIPTION =
            "This is the syntax for the start date and time for the appointment.";
    static final String PREFIX_APPT_END_DATE_AND_TIME_DESCRIPTION =
            "This is the syntax for the end date and time for the appointment.";
    static final String PREFIX_RECUR_YEARS_DESCRIPTION =
            "This is the syntax for the time in years for the appointment to recur.";
    static final String PREFIX_RECUR_MONTHS_DESCRIPTION =
            "This is the syntax for the time in months for the appointment to recur.";
    static final String PREFIX_RECUR_WEEKS_DESCRIPTION =
            "This is the syntax for the time in weeks for the appointment to recur.";
    static final String PREFIX_RECUR_DAYS_DESCRIPTION =
            "This is the syntax for the time in days for the appointment to recur.";
    static final String PREFIX_RECUR_HOURS_DESCRIPTION =
            "This is the syntax for the time in hours for the appointment to recur.";
    static final String PREFIX_RECUR_MINUTES_DESCRIPTION =
            "This is the syntax for the time in minutes for the appointment to recur.";
    static final String PREFIX_APPT_DESC_DESCRIPTION =
            "This is the syntax for the appointment description.";
}
