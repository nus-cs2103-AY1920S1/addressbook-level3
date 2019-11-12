package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_CARDNUMBER = new Prefix("c/");
    public static final Prefix PREFIX_CVC = new Prefix("v/");
    public static final Prefix PREFIX_EXPIRYDATE = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_TITLE = new Prefix("ti/");
    public static final Prefix PREFIX_SORTBY = new Prefix("by/");
    public static final Prefix PREFIX_CONTENT = new Prefix("c/");
    public static final Prefix PREFIX_USERNAME = new Prefix("u/");
    public static final Prefix PREFIX_PASSWORDVALUE = new Prefix("p/");
    public static final Prefix PREFIX_WEBSITE = new Prefix("w/");
    public static final Prefix PREFIX_STRONG = new Prefix("strong/");
    /* Prefix definitions for password generator */
    public static final Prefix PREFIX_LOWER = new Prefix("lower/");
    public static final Prefix PREFIX_UPPER = new Prefix("upper/");
    public static final Prefix PREFIX_NUM = new Prefix("num/");
    public static final Prefix PREFIX_SPECIAL = new Prefix("special/");
    public static final Prefix PREFIX_LENGTH = new Prefix("length/");
}
