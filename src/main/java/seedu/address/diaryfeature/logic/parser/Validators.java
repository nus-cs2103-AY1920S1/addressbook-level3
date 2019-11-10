package seedu.address.diaryfeature.logic.parser;

import java.util.Arrays;
import java.util.Objects;

import seedu.address.diaryfeature.model.details.Username;
import seedu.address.diaryfeature.model.diaryEntry.Memory;
import seedu.address.diaryfeature.model.diaryEntry.Place;
import seedu.address.diaryfeature.model.diaryEntry.Title;

/**
 * Checks user input
 */
class Validators {
    private static final String ALPHANUMERIC_STRING_REGEX = "[a-zA-Z0-9]+";
    private static final String NUMBER_REGEX = "[0-9]+";
    static final int HOUR_LOWER_RANGE = 00;
    static final int HOUR_UPPER_RANGE = 23;
    static final int MIN_LOWER_RANGE = 00;
    static final int MIN_UPPER_RANGE = 59;
    static final int YEAR_LOWER_RANGE = 1980;
    static final int YEAR_UPPER_RANGE = 2030;
    static final int MONTH_LOWER_RANGE = 01;
    static final int MONTH_UPPER_RANGE = 12;
    static final int DAY_LOWER_RANGE = 01;
    static final int DAY_UPPER_RANGE = 31;
    static final int DATE_AND_TIME_LENGTH = 15;

    /**
     * Check if user input is null
     */
    public static boolean isNotNull(String input) {
        if(Objects.isNull(input)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check if user input is empty
     */
    public static boolean isNotEmpty(String input) {
        if(input.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Check if user input is valid title
     */
    public static boolean isValidTitle(String input) {
        boolean answer = false;
        if (isNotEmpty(input)  && isCorrectLength(input,Title.TITLE_MAX_LENGTH)) {
            answer = true;
        }
        return answer;
    }

    /**
     * Check if user input is valid title
     */
    public static boolean isValidPlace(String input) {
        boolean answer = false;
        if (isNotEmpty(input)  && isCorrectLength(input, Place.PLACE_MAX_LENGTH)) {
            answer = true;
        }
        return answer;
    }


    /**
     * Check if user input is valid title
     */
    public static boolean isValidMemory(String input) {
        boolean answer = false;
        if (isNotEmpty(input)  && isCorrectLength(input, Memory.MEMORY_MAX_LENGTH)) {
            answer = true;
        }
        return answer;
    }

    /**
     * Check if user input is correct length(generic)
     */
    public static boolean isCorrectLength(String input,int length) {
        boolean answer = false;
        if(input.length() <= length) {
            answer = true;
        }
        return answer;
    }

    /**
     * Check if user input is for details is correct length
     */

    public static boolean isCorrectDetailsLength(String input,int length) {
        boolean answer = false;
        if(input.length() >= length) {
            answer = true;
        }
        return answer;
    }

    /**
     * Check if user input matches alphanumeric regex
     */

    public static boolean matchesAlphaNumeric(String input) {
        return input.matches(ALPHANUMERIC_STRING_REGEX);
    }

    /**
     * Check if user input is a valid detail
     */
    public static boolean isValidDetail(String input) {
        return isCorrectDetailsLength(input, Username.USERNAME_MIN_LENGTH)
                &&
                matchesAlphaNumeric(input);
    }


    /**
     * Check if user input is a valid date
     */
    public static boolean isCorrectDateFormat(String input) {
        if(input.length() != DATE_AND_TIME_LENGTH) {
            return false;
        }
        String[] dateAndTime = input.split(" ");
        if(dateAndTime.length != 2) {
            return false;
        }
        String time = dateAndTime[1];
        if(time.length() != 4) {
            return false;
        }
        String hourAsString = time.substring(0,1);
        String minAsString = time.substring(2);
        String[] dates = dateAndTime[0].split("/");
        if(dates.length != 3) {
            return false;
        }
        String dayAsString = dates[0];
        String monthAsString = dates[1];
        String yearAsString = dates[2];
        boolean firstCheck = (isValidNumber(hourAsString, HOUR_LOWER_RANGE, HOUR_UPPER_RANGE)
                &&
                isValidNumber(minAsString, MIN_LOWER_RANGE, MIN_UPPER_RANGE)
                &&
                isValidNumber(dayAsString, DAY_LOWER_RANGE, DAY_UPPER_RANGE)
                &&
                isValidNumber(monthAsString, MONTH_LOWER_RANGE, MONTH_UPPER_RANGE)
                &&
                isValidNumber(yearAsString, YEAR_LOWER_RANGE, YEAR_UPPER_RANGE)
        );

        boolean secondCheck = isValidDayMonthYear(getInt(dayAsString),
                getInt(monthAsString), getInt(yearAsString));
        return firstCheck && secondCheck;

    }


    private static boolean isValidDayMonthYear(int day ,int month,int year) {
        int[] oddMonth = {1,3,5,7,8,10,12}; //31 days
        int[] evenMonth = {2,4,6,9,11}; //30 days
        if(Arrays.stream(evenMonth)
                 .anyMatch(x -> x == month) && day == 31) {
            return false;
        } else if(month == 2) {
            if(isLeapYear(year) && day == 29) {
                return true;
            } else if(day == 28) {
                return true;
            } else {
                return false;
            }
        }
        else {
            return true;
        }
    }

    private static boolean isLeapYear(int year) {
        return year%4 == 0;
    }


    /**
     * Change string to int
     */
    private static int getInt(String input) {
        return Integer.parseInt(input);
    }

    /**
     * Check if user input fits in the range
     */
    private static boolean isValidNumber(String input, int lower, int upper) {
        if(!input.matches(NUMBER_REGEX)) {
            return false;
        }
        int test = getInt(input);
        if (test <= upper && test >= lower) {
            return true;
        } else {
            return false;
        }
    }

}
