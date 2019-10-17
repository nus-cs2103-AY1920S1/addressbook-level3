package seedu.address.logic.parser;

import java.util.HashMap;

import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("tt/");

    public static final Prefix PREFIX_TASK = new Prefix("t/");

    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");
    public static final Prefix PREFIX_GOODS = new Prefix("g/");
    public static final Prefix PREFIX_CUSTOMER = new Prefix("c/");

    public static final Prefix PREFIX_DRIVER = new Prefix("d/");

    //Link Syntax to their respective classes
    public static final HashMap<String, String> PREFIX_CLASS_MAP = createPrefixClassMap();

    /**
     * Creates a map for prefix to class name.
     *
     * @return HashMap for prefix to class name.
     */
    public static HashMap<String, String> createPrefixClassMap() {
        HashMap<String, String> classMap = new HashMap<>();
        classMap.put(PREFIX_TASK.getPrefix(), Task.class.getSimpleName());
        classMap.put(PREFIX_CUSTOMER.getPrefix(), Customer.class.getSimpleName());
        classMap.put(PREFIX_DRIVER.getPrefix(), Driver.class.getSimpleName());

        return classMap;
    }
}
