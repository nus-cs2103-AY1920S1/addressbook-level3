package seedu.address.commons.util;

import java.util.Calendar;
import java.util.HashSet;

/**
 * A container for time specific utility functions
 */
public class TimeUtil {
    public static final String BELOW_TWENTY = "Below 20 Years";
    public static final String TWENTY_TO_SIXTYFOUR = "20 - 64 Years";
    public static final String ABOVE_SIXTYFIVE = "65 years & Over";
    public static final String INVALID_YEAR_OF_BIRTH = "Invalid year of birth! Age must be greater than 0.";
    private static final HashSet<String> AGE_GROUP = initializeAgeGroup();

    /**
     * Initialises age group hash set
     *
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
        return AGE_GROUP;
    }

    /**
     * Takes in date of birth and outputs the age group the year is in.
     *
     * @param yearOfBirth
     * @return age group the input year is in.
     */
    public static String parseAgeGroup(int yearOfBirth) {
        int age = getCurrentYear() - yearOfBirth;
        if (age < 0) {
            throw new IllegalArgumentException(INVALID_YEAR_OF_BIRTH);
        }

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
