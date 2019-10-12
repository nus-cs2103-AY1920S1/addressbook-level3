package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.List;

/**
 * Contains Command Line Interface (CLI) syntax definitions common to multiple commands
 */
public class CliSyntax {

    public static final Prefix PREFIX_NAME = new Prefix("n/");
    public static final Prefix PREFIX_PHONE = new Prefix("p/");
    public static final Prefix PREFIX_EMAIL = new Prefix("e/");
    public static final Prefix PREFIX_ADDRESS = new Prefix("a/");
    public static final Prefix PREFIX_TAG = new Prefix("t/");
    public static final Prefix PREFIX_FOOD = new Prefix("f/");

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
    public static final List<Flag> FLAGS = Arrays.asList(new Flag[]{FLAG_NSV, FLAG_SV, FLAG_F, FLAG_P, FLAG_S, FLAG_M});

}
