package cs.f10.t1.nursetraverse.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands.
 * Note that prefix definitions can be duplicated so long as they are not used within the same command.
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_PATIENT_VISIT_TODO = new Prefix("vt/");
    //Do not use with PREFIX_PHONE
    public static final Prefix PREFIX_PATIENT_INDEX = new Prefix ("p/");
    public static final Prefix PREFIX_VISIT_TASK_INDEX_AND_DETAIL = new Prefix ("d/");
    public static final Prefix PREFIX_VISIT_TASK_FINISH = new Prefix("f/");
    public static final Prefix PREFIX_VISIT_TASK_UNFINISH = new Prefix("uf/");
    public static final Prefix PREFIX_VISIT_REMARKS = new Prefix("r/");
    //Do not use with PREFIX_NAME
    public static final Prefix PREFIX_FILENAME = new Prefix("n/");
    public static final Prefix PREFIX_INDEXES = new Prefix("i/");
    // Appointment prefixes
    public static final Prefix PREFIX_APPOINTMENT_START_DATE_AND_TIME = new Prefix("sdt/");
    public static final Prefix PREFIX_APPOINTMENT_END_DATE_AND_TIME = new Prefix("edt/");
    public static final Prefix PREFIX_RECUR_YEARS = new Prefix("ryr/");
    public static final Prefix PREFIX_RECUR_MONTHS = new Prefix("rmon/");
    public static final Prefix PREFIX_RECUR_WEEKS = new Prefix("rweek/");
    public static final Prefix PREFIX_RECUR_DAYS = new Prefix("rday/");
    public static final Prefix PREFIX_RECUR_HOURS = new Prefix("rhr/");
    public static final Prefix PREFIX_RECUR_MINUTES = new Prefix("rmin/");
    public static final Prefix PREFIX_APPOINTMENT_DESCRIPTION = new Prefix("desc/");
}
