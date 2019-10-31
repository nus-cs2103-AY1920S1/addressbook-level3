package seedu.algobase.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Field */
    public static final Prefix PREFIX_AUTHOR = new Prefix("a/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_DIFFICULTY = new Prefix("diff/");
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_REMARK = new Prefix("r/");
    public static final Prefix PREFIX_SOURCE = new Prefix("src/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_WEBLINK = new Prefix("w/");
    /* Sorting */
    public static final Prefix PREFIX_SORTING_METHOD = new Prefix("m/");
    public static final Prefix PREFIX_SORTING_ORDER = new Prefix("ord/");
    /* Model */
    public static final Prefix PREFIX_PLAN = new Prefix("plan/");
    public static final Prefix PREFIX_PLAN_FROM = new Prefix("from/");
    public static final Prefix PREFIX_PLAN_TO = new Prefix("to/");
    public static final Prefix PREFIX_PROBLEM = new Prefix("prob/");
    public static final Prefix PREFIX_TASK = new Prefix("task/");
    /* Date */
    public static final Prefix PREFIX_DUE_DATE = new Prefix("due/");
    public static final Prefix PREFIX_START_DATE = new Prefix("start/");
    public static final Prefix PREFIX_END_DATE = new Prefix("end/");
    /* Storage */
    public static final Prefix PREFIX_FORMAT = new Prefix("format/");
    public static final Prefix PREFIX_PATH = new Prefix("path/");
    /* UI */
    public static final Prefix PREFIX_TAB_TYPE = new Prefix("tt/");
    public static final Prefix PREFIX_TAB_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_MODEL_TYPE = new Prefix("m/");
    public static final Prefix PREFIX_MODEL_INDEX = new Prefix("i/");
    /* Util */
    public static final Prefix FLAG_FORCE = new Prefix("f/");

}
