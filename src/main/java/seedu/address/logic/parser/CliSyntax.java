package seedu.address.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */

    /* User Profile */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PRIMARY_MUSCLE = new Prefix("p/");
    public static final Prefix PREFIX_INTENSITY = new Prefix("i/");
    public static final Prefix PREFIX_DISTANCE = new Prefix("d/");
    public static final Prefix PREFIX_REPETITIONS = new Prefix("r/");
    public static final Prefix PREFIX_SETS = new Prefix("s/");
    public static final Prefix PREFIX_WEIGHT = new Prefix("w/");

    /* Recipe Book */
    public static final Prefix PREFIX_INGREDIENT = new Prefix("i/");
    public static final Prefix PREFIX_CALORIES = new Prefix("cal/");
    public static final Prefix PREFIX_CARBS = new Prefix("c/");
    public static final Prefix PREFIX_FATS = new Prefix("f/");
    public static final Prefix PREFIX_PROTEIN = new Prefix("p/");
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
    public static final Prefix PREFIX_DOB = new Prefix("d/");
    public static final Prefix PREFIX_BLOODTYPE = new Prefix("bt/");
    public static final Prefix PREFIX_HEIGHT = new Prefix("h/");
    public static final Prefix PREFIX_MEDICALHISTORY = new Prefix("m/");

    /* Health Records */
    public static final Prefix PREFIX_ID = new Prefix("id/");
    public static final Prefix PREFIX_TYPE = new Prefix("t/");
    public static final Prefix PREFIX_VALUE = new Prefix("val/");
    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");

    /* Prefix definitions */
    public static final Prefix PREFIX_DIARY_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PAGE_NUMBER = new Prefix("num/");
    public static final Prefix PREFIX_PAGE_TITLE = new Prefix("t/");
}
