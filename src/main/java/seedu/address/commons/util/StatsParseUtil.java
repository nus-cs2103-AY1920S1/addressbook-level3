package seedu.address.commons.util;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * utility class to check stats Input
 */
public class StatsParseUtil {

    public static final Optional<Calendar> MIN_DATE = Optional.of(new Calendar
            .Builder()
            .setInstant(new Date(Long.MIN_VALUE))
            .build());

    public static final Optional<Calendar> MAX_DATE = Optional.of(new Calendar
            .Builder()
            .setInstant(new Date(Long.MAX_VALUE))
            .build());


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
        return typeList.stream().anyMatch(x -> x.toUpperCase().contains(test.toUpperCase()));
    }
    //generate-s s/PROFIT d1/2018.12.12 d2/2019.12.12
}
