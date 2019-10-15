package seedu.address.calendar.parser;

import seedu.address.calendar.model.MonthOfYear;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MonthParser {
    private static final String FORMAT_ERROR_MESSAGE = "Incorrect month format.";
    private static final String MONTH_ERROR_MESSAGE = "No such month can be found.";

    private static final String MONTH_STR_KEY = "monthStr";
    private static final String MONTH_STR_PATTERN = "(?<" + MONTH_STR_KEY + ">\\S{3,})";
    private static final String MONTH_NUM_KEY = "monthNum";
    private static final String MONTH_NUM_PATTERN = "(?<" + MONTH_NUM_KEY + ">\\d{1,2}?)";
    private static final Pattern MONTH_FORMAT = Pattern.compile(MONTH_NUM_PATTERN + "|" + MONTH_STR_PATTERN);

    MonthOfYear parse(String userInput) throws ParseException {
        final Matcher matcher = MONTH_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            throw new ParseException(FORMAT_ERROR_MESSAGE);
        }

        MonthOfYear month;

        if (matcher.group(MONTH_NUM_KEY) != null) {
            String monthNum = matcher.group(MONTH_NUM_PATTERN);
            month = convertMonthNum(monthNum);
        } else {
            String monthStr = matcher.group(MONTH_STR_KEY);
            month = convertMonthStr(monthStr);
        }

        return month;
    }

    private MonthOfYear convertMonthNum(String monthNum) throws ParseException {
        // assumes that user represents Jan with 1, Feb with 2, etc.
        int zeroBasedMonth = Integer.parseInt(monthNum) - 1;

        if (!MonthOfYear.isValidMonthNum(zeroBasedMonth)) {
            throw new ParseException(MONTH_ERROR_MESSAGE);
        }

        MonthOfYear month = MonthOfYear.convertNumToMonth(zeroBasedMonth);
        return month;
    }

    private MonthOfYear convertMonthStr(String monthStr) throws ParseException {
        if (!MonthOfYear.isValidMonthStr(monthStr)) {
            throw new ParseException(MONTH_ERROR_MESSAGE);
        }

        MonthOfYear month = MonthOfYear.convertStrToMonth(monthStr);
        return month;
    }
}
