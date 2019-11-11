package seedu.address.logic.parser;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import seedu.address.model.person.Customer;
import seedu.address.model.person.Driver;
import seedu.address.model.task.Task;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_CUSTOMER = new Prefix("c/");
    public static final Prefix PREFIX_DRIVER = new Prefix("d/");
    public static final Prefix PREFIX_TASK = new Prefix("t/");

    /**
     * For Persons (customer, driver)
     */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("tt/");
    public static final Prefix PREFIX_RATING = new Prefix("r/");

    /**
     * For Tasks
     */
    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");
    public static final Prefix PREFIX_EVENT_TIME = new Prefix("at/");
    public static final Prefix PREFIX_GOODS = new Prefix("g/");

    /**
     * For Company Information
     */
    public static final Prefix PREFIX_FAX = new Prefix("f/");
    public static final Prefix PREFIX_REGISTRATION_NUMBER = new Prefix("r/");
    public static final Prefix PREFIX_GST_REGISTRATION_NUMBER = new Prefix("gst/");

    /**
     * For Pdf document
     */
    public static final Prefix PREFIX_PDF = new Prefix("pdf/");

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

    public static Prefix[] getAllPrefixes() {
        Field[] fields = CliSyntax.class.getDeclaredFields();
        List<Prefix> result = new ArrayList<>();
        for (Field field : fields) {
            int modifier = field.getModifiers();
            if ((!Modifier.isFinal(modifier)) || (!Modifier.isStatic(modifier))) {
                continue;
            }

            if (!field.getType().getSimpleName().equals("Prefix")) {
                continue;
            }

            try {
                result.add((Prefix) field.get(new Object()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result.toArray(new Prefix[1]);
    }
}

