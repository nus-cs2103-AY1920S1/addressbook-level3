package dukecooks.logic.parser;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    /* Prefix definitions */

    /* Dashboard */
    public static final Prefix PREFIX_TASKNAME = new Prefix("tn/");
    public static final Prefix PREFIX_TASKDATE = new Prefix("td/");

    /* Workout and Exercises */
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PRIMARY_MUSCLE = new Prefix("p/");
    public static final Prefix PREFIX_SECONDARY_MUSCLE = new Prefix("sm/");
    public static final Prefix PREFIX_INTENSITY = new Prefix("i/");
    public static final Prefix PREFIX_DISTANCE = new Prefix("d/");
    public static final Prefix PREFIX_REPETITIONS = new Prefix("r/");
    public static final Prefix PREFIX_SETS = new Prefix("s/");
    public static final Prefix PREFIX_WEIGHT = new Prefix("w/");
    public static final Prefix PREFIX_TIMING = new Prefix("t/");
    public static final Prefix PREFIX_WORKOUT_NAME = new Prefix("n/");
    public static final Prefix PREFIX_EXERCISE_INDEX = new Prefix("ei/");
    public static final Prefix PREFIX_WORKOUT_INDEX = new Prefix("wi/");

    /* Recipe Book */
    public static final Prefix PREFIX_INGREDIENT = new Prefix("i/");
    public static final Prefix PREFIX_REMOVEINGREDIENT = new Prefix("i-/");
    public static final Prefix PREFIX_CALORIES = new Prefix("cal/");
    public static final Prefix PREFIX_CARBS = new Prefix("carb/");
    public static final Prefix PREFIX_FATS = new Prefix("fats/");
    public static final Prefix PREFIX_PROTEIN = new Prefix("prot/");

    /* Meal Plan Book */
    public static final Prefix PREFIX_DAY1 = new Prefix("day1/");
    public static final Prefix PREFIX_REMOVEDAY1 = new Prefix("day1-/");
    public static final Prefix PREFIX_DAY2 = new Prefix("day2/");
    public static final Prefix PREFIX_REMOVEDAY2 = new Prefix("day2-/");
    public static final Prefix PREFIX_DAY3 = new Prefix("day3/");
    public static final Prefix PREFIX_REMOVEDAY3 = new Prefix("day3-/");
    public static final Prefix PREFIX_DAY4 = new Prefix("day4/");
    public static final Prefix PREFIX_REMOVEDAY4 = new Prefix("day4-/");
    public static final Prefix PREFIX_DAY5 = new Prefix("day5/");
    public static final Prefix PREFIX_REMOVEDAY5 = new Prefix("day5-/");
    public static final Prefix PREFIX_DAY6 = new Prefix("day6/");
    public static final Prefix PREFIX_REMOVEDAY6 = new Prefix("day6-/");
    public static final Prefix PREFIX_DAY7 = new Prefix("day7/");
    public static final Prefix PREFIX_REMOVEDAY7 = new Prefix("day7-/");

    /* Health Records */
    public static final Prefix PREFIX_TYPE = new Prefix("t/");
    public static final Prefix PREFIX_VALUE = new Prefix("val/");
    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");
    public static final Prefix PREFIX_REMARK = new Prefix("remark/");
    public static final Prefix PREFIX_REMOVEREMARK = new Prefix("remark-/");

    /* User Profile */
    // prefix name shared with recipe book
    public static final Prefix PREFIX_GENDER = new Prefix("g/");
    public static final Prefix PREFIX_DOB = new Prefix("d/");
    public static final Prefix PREFIX_BLOODTYPE = new Prefix("bt/");
    // prefix weight shared with workout/exercises
    public static final Prefix PREFIX_HEIGHT = new Prefix("h/");
    public static final Prefix PREFIX_MEDICALHISTORY = new Prefix("m/");
    public static final Prefix PREFIX_REMOVEMEDICALHISTORY = new Prefix("m-/");

    /* Diary Records */
    public static final Prefix PREFIX_DIARY_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PAGE_DESCRIPTION = new Prefix("desc/");
    public static final Prefix PREFIX_PAGE_TITLE = new Prefix("t/");
    public static final Prefix PREFIX_PAGE_TYPE = new Prefix("tp/");

    /* Common */
    public static final Prefix PREFIX_IMAGE = new Prefix("i/");
}
