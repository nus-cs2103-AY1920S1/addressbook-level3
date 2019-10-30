package dukecooks.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */

    /* Dashboard */
    public static final Prefix PREFIX_TASKNAME = new Prefix("tn/");
    public static final Prefix PREFIX_TASKDATE = new Prefix("td/");

    /* User Profile */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PRIMARY_MUSCLE = new Prefix("p/");
    public static final Prefix PREFIX_SECONDARY_MUSCLE = new Prefix("sm/");
    public static final Prefix PREFIX_INTENSITY = new Prefix("i/");
    public static final Prefix PREFIX_DISTANCE = new Prefix("d/");
    public static final Prefix PREFIX_REPETITIONS = new Prefix("r/");
    public static final Prefix PREFIX_SETS = new Prefix("s/");
    public static final Prefix PREFIX_WEIGHT = new Prefix("w/");

    /* Recipe Book */
    public static final Prefix PREFIX_INGREDIENT = new Prefix("i/");
    public static final Prefix PREFIX_REMOVEINGREDIENT = new Prefix("i-/");
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

    /* Diary Records */
    public static final Prefix PREFIX_DIARY_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PAGE_DESCRIPTION = new Prefix("desc/");
    public static final Prefix PREFIX_PAGE_TITLE = new Prefix("t/");

    /* Common */
    public static final Prefix PREFIX_IMAGE = new Prefix("i/");
}
