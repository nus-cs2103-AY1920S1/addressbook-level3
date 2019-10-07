package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */

    //// Customer
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_CONTACT = new Prefix("c/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");

    //// Phone
    public static final Prefix PREFIX_BRAND = new Prefix("b/");
    public static final Prefix PREFIX_CAPACITY = new Prefix("cp/");
    public static final Prefix PREFIX_COLOUR = new Prefix("cl/");
    public static final Prefix PREFIX_PRICE = new Prefix("$/");


    //// Order
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_CUSTOMER = new Prefix("c/");
    // price + tags

    //// Schedule
    public static final Prefix PREFIX_ORDER = new Prefix("o/");
    public static final Prefix PREFIX_CALENDAR = new Prefix("c/");
    public static final Prefix PREFIX_VENUE = new Prefix("v/");


    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");



}
