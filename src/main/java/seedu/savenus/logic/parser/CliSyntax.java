package seedu.savenus.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PRICE = new Prefix("p/");
    public static final Prefix PREFIX_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_CATEGORY = new Prefix("c/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_LOCATION = new Prefix("l/");
    public static final Prefix PREFIX_OPENING_HOURS = new Prefix("o/");
    public static final Prefix PREFIX_RESTRICTIONS = new Prefix("r/");

    /* Field Definitions */
    public static final String FIELD_NAME_CATEGORY = "CATEGORY";
    public static final String FIELD_NAME_DESCRIPTION = "DESCRIPTION";
    public static final String FIELD_NAME_LOCATION = "LOCATION";
    public static final String FIELD_NAME_NAME = "NAME";
    public static final String FIELD_NAME_OPENING_HOURS = "OPENING_HOURS";
    public static final String FIELD_NAME_PRICE = "PRICE";
    public static final String FIELD_NAME_RESTRICTIONS = "RESTRICTIONS";

    /* Direction Definitions */
    public static final String ASCENDING_DIRECTION = "ASC";
    public static final String DESCENDING_DIRECTION = "DESC";

    /* Quantifier Definitions */
    public static final String QUANTIFY_LESS_THAN = "LESS_THAN";
    public static final String QUANTIFY_EQUALS_TO = "EQUALS_TO";
    public static final String QUANTIFY_MORE_THAN = "MORE_THAN";
}
