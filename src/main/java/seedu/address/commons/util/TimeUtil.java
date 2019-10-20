package seedu.address.commons.util;

import java.util.Calendar;
import java.util.HashSet;

// TODO: Write test
/**
 * A container for time specific utility functions
 */
public class TimeUtil {
    private static final String BELOW_TWENTY = "Below 20 Years";
    private static final String TWENTY_TO_SIXTYFOUR = "20 - 64 Years";
    private static final String ABOVE_SIXTYFIVE = "65 years & Over";
    private static HashSet<String> AgeGroup = initializeAgeGroup();

    /**
     * Initialises age group hash set
     * @return age group hash set
     */
    private static HashSet<String> initializeAgeGroup() {
        HashSet<String> result = new HashSet<>();
        result.add(BELOW_TWENTY);
        result.add(TWENTY_TO_SIXTYFOUR);
        result.add(ABOVE_SIXTYFIVE);
        return result;
    }

    public static HashSet<String> getAgeGroup() {
        return AgeGroup;
    }

    /**
     * Takes in date of birth and outputs the age group the year is in.
     * @param yearOfBirth
     * @return age group the input year is in.
     */
    public static String parseAgeGroup(int yearOfBirth) {
        int age = getCurrentYear() - yearOfBirth;
        if (age < 20) {
            return BELOW_TWENTY;
        } else if (age < 65) {
            return TWENTY_TO_SIXTYFOUR;
        } else {
            return ABOVE_SIXTYFIVE;
        }
    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        return currentYear;
    }
}
