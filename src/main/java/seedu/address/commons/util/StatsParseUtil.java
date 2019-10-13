package seedu.address.commons.util;

/**
 * utility class to check stats Input
 */
public class StatsParseUtil {

    public static final String DATE_MESSAGE_CONSTRAINTS =
            "Date should only contain numbers in the format of DD/MM/YYYY, and it should not be blank";

    public static final String STATS_MESSAGE_CONSTRAINTS =
            "Stat type should only be either PROFIT, REVENUE or COST, and should not be blank.";
    /*
     * The word of the stat type must be one of the three below,
     */
    private static final String STAT_TYPE_VALIDATION_REGEX = "\b(PROFIT|REVENUE|COST)\b";

    /**
     * Returns true if a given string is a valid Stat Type.
     */
    public static boolean isValidStatType(String test) {
        return test.matches(STAT_TYPE_VALIDATION_REGEX);
    }

}
