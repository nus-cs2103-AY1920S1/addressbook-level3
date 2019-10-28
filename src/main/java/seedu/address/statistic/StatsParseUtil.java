package seedu.address.statistic;

import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * utility class to check stats Input
 */
public class StatsParseUtil {

    public static final Calendar MIN_DATE = new Calendar
            .Builder()
            .setInstant(new Date(Long.MIN_VALUE))
            .build();

    public static final Calendar MAX_DATE = new Calendar
            .Builder()
            .setInstant(new Date(Long.MAX_VALUE))
            .build();

    /**
     * list of valid stat types
     */
    private static final String regexList = "\\bprofit\\b|\\bcost\\b|\\brevenue\\b";

    /**
     * Returns true if a given string is a valid Stat Type.
     */
    public static boolean isValidStatType(String test) {
        Pattern p = Pattern.compile(regexList);
        Matcher m = p.matcher(test);
        return m.find();
    }
    //generate-s s/PROFIT d1/2018.12.12 d2/2019.12.12
}
