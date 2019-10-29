package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.List;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    // Biography Prefixes
    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_DP_PATH = new Prefix("dp/");
    public static final Prefix PREFIX_PROFILE_DESC = new Prefix("desc/");
    public static final Prefix PREFIX_NRIC = new Prefix("nric/");
    public static final Prefix PREFIX_GENDER = new Prefix("gender/");
    public static final Prefix PREFIX_DATE_OF_BIRTH = new Prefix("dob/");
    public static final Prefix PREFIX_CONTACT_NUMBER = new Prefix("p/");
    public static final Prefix PREFIX_EMERGENCY_CONTACT = new Prefix("e/");
    public static final Prefix PREFIX_MEDICAL_CONDITION = new Prefix("m/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_GOALS = new Prefix("goal/");
    public static final Prefix PREFIX_OTHER_BIO_INFO = new Prefix("o/");

    // Aesthetics Prefixes
    public static final Prefix PREFIX_BG_SIZE = new Prefix("s/");
    public static final Prefix PREFIX_BG_REPEAT = new Prefix("r/");

    // Record Prefixes
    public static final Prefix PREFIX_RECORDTYPE = new Prefix("rt/");
    public static final Prefix PREFIX_DATETIME = new Prefix("dt/");

    public static final Prefix PREFIX_BLOODSUGAR_CONCENTRATION = new Prefix("con/");

    public static final Prefix PREFIX_BMI_HEIGHT = new Prefix("h/");
    public static final Prefix PREFIX_BMI_WEIGHT = new Prefix("w/");

    // Stats Prefixes
    public static final Prefix PREFIX_AVGTYPE = new Prefix("a/");
    public static final Prefix PREFIX_COUNT = new Prefix("n/");

    // Calendar Prefixes
    public static final Prefix PREFIX_CALENDAR_DESCRIPTION = new Prefix("d/");
    public static final Prefix PREFIX_CALENDAR_REPETITION = new Prefix("r/");
    public static final Prefix PREFIX_TIME_DURATION = new Prefix("td/");
    public static final Prefix PREFIX_YEAR_MONTH = new Prefix("ym/");
    public static final Prefix PREFIX_YEAR_MONTH_DAY = new Prefix("ymd/");
    public static final Prefix PREFIX_YEAR_MONTH_WEEK = new Prefix("ymw/");

    // Food Prefixes
    public static final Prefix PREFIX_FOOD = new Prefix("fn/");
    public static final Prefix PREFIX_FOOD_TYPE = new Prefix("ft/");
    public static final Prefix PREFIX_CALORIE = new Prefix("ca/");
    public static final Prefix PREFIX_GI = new Prefix("gi/");
    public static final Prefix PREFIX_SUGAR = new Prefix("su/");
    public static final Prefix PREFIX_FAT = new Prefix("fa/");

    // Food Recommendation Prefixes
    public static final String ABBR_NON_STARCHY_VEGETABLE = "nsv";
    public static final String ABBR_STARCHY_VEGETABLE = "sv";
    public static final String ABBR_FRUIT = "f";
    public static final String ABBR_PROTEIN = "p";
    public static final String ABBR_SNACK = "s";
    public static final String ABBR_MEAL = "m";
    public static final String FLAG_SIGNAL = "-";

    public static final Flag FLAG_NSV = new Flag(FLAG_SIGNAL + ABBR_NON_STARCHY_VEGETABLE);
    public static final Flag FLAG_SV = new Flag(FLAG_SIGNAL + ABBR_STARCHY_VEGETABLE);
    public static final Flag FLAG_F = new Flag(FLAG_SIGNAL + ABBR_FRUIT);
    public static final Flag FLAG_P = new Flag(FLAG_SIGNAL + ABBR_PROTEIN);
    public static final Flag FLAG_S = new Flag(FLAG_SIGNAL + ABBR_SNACK);
    public static final Flag FLAG_M = new Flag(FLAG_SIGNAL + ABBR_MEAL);
    public static final List<Flag> FLAGS = Arrays.asList(FLAG_NSV, FLAG_SV, FLAG_F, FLAG_P, FLAG_S, FLAG_M);
}
