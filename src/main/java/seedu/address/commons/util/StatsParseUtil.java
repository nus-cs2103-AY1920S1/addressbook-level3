package seedu.address.commons.util;

import java.util.Arrays;
import java.util.List;

/**
 * utility class to check stats Input
 */
public class StatsParseUtil {

    public static final String DATE_MESSAGE_CONSTRAINTS =
            "Date should only contain numbers in the format of YYYY.MM.DD , and it should not be blank";

    public static final String STATS_MESSAGE_CONSTRAINTS =
            "Stat type should only be either PROFIT, REVENUE or COST, and should not be blank.";
    /*
     * The word of the stat type must be one of the three below,
     */
    private static final String STAT_TYPE_VALIDATION_REGEX = "\b(?<!@)(PROFIT|REVENUE|COST)\b";

    /**
     * list of valid stat types
     */
    private static final List<String> typeList = Arrays.asList("PROFIT", "COST", "REVENUE");

    /**
     * Returns true if a given string is a valid Stat Type.
     */
    public static boolean isValidStatType(String test) {
        return typeList.stream().anyMatch( x -> x.toUpperCase().contains(test.toUpperCase()));
    }
    //generate-s s/PROFIT d1/2018.12.12 d2/2019.12.12
}
