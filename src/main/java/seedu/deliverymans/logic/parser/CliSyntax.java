package seedu.deliverymans.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_REMARK = new Prefix("rr/");
    public static final Prefix PREFIX_CUSTOMER = new Prefix("c/");
    public static final Prefix PREFIX_DELIVERYMAN = new Prefix("d/");
    public static final Prefix PREFIX_RESTAURANT = new Prefix("r/");
    public static final Prefix PREFIX_FOOD = new Prefix("f/");
    public static final Prefix PREFIX_BOOLEAN_COMPLETED = new Prefix("completed/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("q/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");
    // Prefix for universal commands


    // Prefix for deliveryman commands

    // Prefix for restaurant commands
    public static final Prefix PREFIX_PRICE = new Prefix("a/");
}
