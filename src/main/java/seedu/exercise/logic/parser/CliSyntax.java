package seedu.exercise.logic.parser;

import java.util.HashSet;
import java.util.Set;

/**
 * Contains Command Line Interface (CLI) syntax definitions and methods common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions for default properties */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DATE = new Prefix("d/");
    public static final Prefix PREFIX_CALORIES = new Prefix("c/");
    public static final Prefix PREFIX_QUANTITY = new Prefix("q/");
    public static final Prefix PREFIX_MUSCLE = new Prefix("m/");
    public static final Prefix PREFIX_UNIT = new Prefix("u/");

    /* Prefix definitions for add, delete and edit commands */
    public static final Prefix PREFIX_CATEGORY = new Prefix("t/");
    public static final Prefix PREFIX_INDEX = new Prefix("i/");

    /* Prefix definitions for resolve command */
    public static final Prefix PREFIX_CONFLICT_INDEX = new Prefix("r/");

    /* Prefix definitions for custom command */
    public static final Prefix PREFIX_CUSTOM_NAME = new Prefix("s/");
    public static final Prefix PREFIX_FULL_NAME = new Prefix("f/");
    public static final Prefix PREFIX_PARAMETER_TYPE = new Prefix("p/");
    public static final Prefix PREFIX_REMOVE_CUSTOM = new Prefix("rm/");

    /* Prefix definition for suggest command */
    public static final Prefix PREFIX_SUGGEST_TYPE = new Prefix("s/");
    public static final Prefix PREFIX_OPERATION_TYPE = new Prefix("o/");

    /* Prefix definition for statistic command */
    public static final Prefix PREFIX_CHART = new Prefix("h/");
    public static final Prefix PREFIX_START_DATE = new Prefix("s/");
    public static final Prefix PREFIX_END_DATE = new Prefix("e/");

    /* A set consisting of property prefix definitions for add and edit commands */
    private static final Set<Prefix> PROPERTY_PREFIXES_SET = new HashSet<>();

    /**
     * Updates the prefixes in {@code PREFIXES_SET} with the input {@code prefixes}.
     */
    public static void setPropertyPrefixesSet(Set<Prefix> prefixes) {
        PROPERTY_PREFIXES_SET.addAll(prefixes);
        PROPERTY_PREFIXES_SET.retainAll(prefixes);
    }

    /**
     * Returns an array that contains the prefixes in {@code PREFIXES_SET}.
     * This prefix array can be used for {@link ArgumentTokenizer#tokenize}.
     */
    public static Prefix[] getPropertyPrefixesArray() {
        return PROPERTY_PREFIXES_SET.toArray(new Prefix[0]);
    }
}
