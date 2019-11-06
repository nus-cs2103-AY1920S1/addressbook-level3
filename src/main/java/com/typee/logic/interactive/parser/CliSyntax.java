package com.typee.logic.interactive.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_ENGAGEMENT_TYPE = new Prefix("t/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_START_TIME = new Prefix("s/");
    public static final Prefix PREFIX_END_TIME = new Prefix("e/");
    public static final Prefix PREFIX_PRIORITY = new Prefix("p/");
    public static final Prefix PREFIX_ATTENDEES = new Prefix("a/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_TAB = new Prefix("b/");
    public static final Prefix PREFIX_PROPERTY = new Prefix("p/");
    public static final Prefix PREFIX_ORDER = new Prefix("o/");
    public static final Prefix PREFIX_CALENDAR = new Prefix("c/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    //Report Generation 23/10/19 Ko Gi Hun
    public static final Prefix PREFIX_LIST_INDEX = new Prefix("i/");
    public static final Prefix PREFIX_TO = new Prefix("t/");
    public static final Prefix PREFIX_FROM = new Prefix("f/");
}
