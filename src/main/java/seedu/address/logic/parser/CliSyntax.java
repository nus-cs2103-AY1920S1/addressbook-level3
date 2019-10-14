package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for task */
    public static final Prefix PREFIX_TASK_NAME = new Prefix("tn/");
    public static final Prefix PREFIX_TASK_STATUS = new Prefix("s/");
    public static final Prefix PREFIX_TASK_TAG = new Prefix("t/");

    /*Prefix definitions for inventory*/
    public static final Prefix PREFIX_INVENTORY_NAME = new Prefix("i/");
    public static final Prefix PREFIX_INVENTORY_PRICE = new Prefix("p/");
    public static final Prefix PREFIX_INVENTORY_TASKID = new Prefix("ti/");

}
