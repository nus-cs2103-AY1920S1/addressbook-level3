package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */

    /* User Profile */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
    public static final Prefix PREFIX_DOB = new Prefix("d/");
    public static final Prefix PREFIX_BLOODTYPE = new Prefix("bt/");
    public static final Prefix PREFIX_WEIGHT = new Prefix("w/");
    public static final Prefix PREFIX_HEIGHT = new Prefix("h/");
    public static final Prefix PREFIX_MEDICALHISTORY = new Prefix("m/");

    /* Health Records */
    public static final Prefix PREFIX_ID = new Prefix("id/");
    public static final Prefix PREFIX_TYPE = new Prefix("t/");
    public static final Prefix PREFIX_VALUE = new Prefix("val/");
    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");

}
