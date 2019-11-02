package seedu.address.diaryfeature.logic.parser;

import java.util.Objects;

import seedu.address.diaryfeature.model.diaryEntry.Title;

public class Validators {
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

    public static boolean isNotNull(String input) {
        if(Objects.isNull(input)) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isNotEmpty(String input) {
        if(input.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isValidTitle(String input) {
        boolean answer = false;
        if (isNotEmpty(input)  && isCorrectLength(input,Title.TITLE_MAX_LENGTH)) {
            answer = true;
        }
        return answer;
    }

    public static boolean isCorrectLength(String input,int length) {
        boolean answer = false;
        if(input.length() <= length) {
            answer = true;
        }
        return answer;
    }

    public static boolean isCorrectDateFormat(String input) {
        if(input.length() != DATE_AND_TIME_LENGTH) {
            return false;
        }
        String[] dateAndTime = input.split(" ");
        System.out.println(input);
        String time = dateAndTime[1];
        String hourAsString = time.substring(0,1);
        String minAsString = time.substring(2);
        String[] dates = dateAndTime[0].split("/");
        String dayAsString = dates[0];
        String monthAsString = dates[1];
        String yearAsString = dates[2];
        return (isValidNumber(hourAsString, HOUR_LOWER_RANGE, HOUR_UPPER_RANGE)
                &&
                isValidNumber(minAsString, MIN_LOWER_RANGE, MIN_UPPER_RANGE)
                &&
                isValidNumber(dayAsString, DAY_LOWER_RANGE, DAY_UPPER_RANGE)
                &&
                isValidNumber(monthAsString, MONTH_LOWER_RANGE, MONTH_UPPER_RANGE)
                &&
                isValidNumber(yearAsString, YEAR_LOWER_RANGE, YEAR_UPPER_RANGE)
        );


    }

    private static int getInt(String input) {
        return Integer.parseInt(input);
    }

    private static boolean isValidNumber(String input, int lower, int upper) {
        int test = getInt(input);
        if (test <= upper && test >= lower) {
            return true;
        } else {
            return false;
        }
    }

}
