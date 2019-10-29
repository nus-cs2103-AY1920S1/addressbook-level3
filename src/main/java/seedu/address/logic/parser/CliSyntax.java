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
    public static final Prefix PREFIX_IDENTITY_NUM = new Prefix("i/");
    public static final Prefix PREFIX_SERIAL_NUM = new Prefix("s/");
    public static final Prefix PREFIX_PHONE_NAME = new Prefix("n/");
    public static final Prefix PREFIX_BRAND = new Prefix("b/");
    public static final Prefix PREFIX_CAPACITY = new Prefix("cp/");
    public static final Prefix PREFIX_COLOUR = new Prefix("cl/");
    public static final Prefix PREFIX_COST = new Prefix("$/");


    //// Order
    public static final Prefix PREFIX_ORDERID = new Prefix("i/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_CUSTOMER = new Prefix("c/");
    public static final Prefix PREFIX_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_PRICE = new Prefix("$/");
    // price + tags

    //// Schedule
    public static final Prefix PREFIX_DATE = new Prefix("cd/");
    public static final Prefix PREFIX_TIME = new Prefix("ct/");
    public static final Prefix PREFIX_VENUE = new Prefix("v/");
    public static final Prefix PREFIX_ALLOW = new Prefix ("-allow");

    //// Stats
    public static final Prefix PREFIX_STAT_TYPE = new Prefix("s/");
    public static final Prefix PREFIX_STARTING_DATE = new Prefix("d1/");
    public static final Prefix PREFIX_ENDING_DATE = new Prefix("d2/");

    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");



}
