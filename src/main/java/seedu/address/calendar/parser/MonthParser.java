package seedu.address.calendar.parser;

import seedu.address.calendar.model.MonthOfYear;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MonthParser {
    private static final String MONTH_STR_KEY = "monthStr";
    private static final String MONTH_STR_PATTERN = "(?<" + MONTH_STR_KEY + ">\\S{3,})";
    private static final String MONTH_NUM_KEY = "monthNum";
    private static final String MONTH_NUM_PATTERN = "(?<" + MONTH_NUM_KEY + ">\\d{1,2}?)";
    private static final Pattern MONTH_FORMAT = Pattern.compile(MONTH_NUM_PATTERN + "|" + MONTH_STR_PATTERN);

    MonthOfYear parse(String userInput) {
        final Matcher matcher = MONTH_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            // todo: handle exception
            System.out.println("Incorrect month format");
        }

        MonthOfYear month;

        if (matcher.group(MONTH_NUM_KEY) != null) {
            // assumes that user represents Jan with 1, Feb with 2, etc.
            int zeroBasedMonth = Integer.parseInt(matcher.group(MONTH_NUM_PATTERN)) - 1;
            month = MonthOfYear.convertNumToMonth(zeroBasedMonth);
        } else {
            String monthStr = matcher.group(MONTH_STR_KEY);
            month = MonthOfYear.convertStrToMonth(monthStr);
        }

        return month;
    }
}
