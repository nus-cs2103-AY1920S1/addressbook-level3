package seedu.address.calendar.logic.parser;

import seedu.address.calendar.model.util.DateUtil;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class MonthParser {
    private static final String FORMAT_ERROR_MESSAGE = "Incorrect month format.";
    private static final String MONTH_ERROR_MESSAGE = "No such month can be found.";
    private static final String MONTH_EXTRA_ARG = "after month";

    private static final String MONTH_STR_KEY = "monthStr";
    private static final String MONTH_STR_PATTERN = "(?<" + MONTH_STR_KEY + ">\\S{3,})";
    private static final String MONTH_NUM_KEY = "monthNum";
    private static final String MONTH_NUM_PATTERN = "(?<" + MONTH_NUM_KEY + ">\\d{1,2}?)";
    private static final Pattern MONTH_FORMAT = Pattern.compile(MONTH_NUM_PATTERN + "|" + MONTH_STR_PATTERN);

    Optional<MonthOfYear> parse(Optional<String> monthInput) throws ParseException {
        if (monthInput.isEmpty()) {
            return Optional.empty();
        }

        String input = monthInput.get().trim();

        if (ParserUtil.hasCharInValue(input)) {
            throw new ParseException(String.format(ParserUtil.MESSAGE_ARG_EXTRA, MONTH_EXTRA_ARG));
        }

        final Matcher matcher = MONTH_FORMAT.matcher(input.trim());

        if (!matcher.matches()) {
            throw new ParseException(FORMAT_ERROR_MESSAGE);
        }

        MonthOfYear month;

        if (matcher.group(MONTH_NUM_KEY) != null) {
            String monthNum = matcher.group(MONTH_NUM_KEY);
            month = convertMonthNum(monthNum);
        } else {
            String monthStr = matcher.group(MONTH_STR_KEY);
            month = convertMonthStr(monthStr);
        }

        return Optional.of(month);
    }

    private MonthOfYear convertMonthNum(String monthNum) throws ParseException {
        // assumes that user represents Jan with 1, Feb with 2, etc.
        int zeroBasedMonth = Integer.parseInt(monthNum) - 1;

        if (!DateUtil.isValidMonthNum(zeroBasedMonth)) {
            throw new ParseException(MONTH_ERROR_MESSAGE);
        }

        MonthOfYear month = DateUtil.convertNumToMonth(zeroBasedMonth);
        return month;
    }

    private MonthOfYear convertMonthStr(String monthStr) throws ParseException {
        if (!DateUtil.isValidMonthStr(monthStr)) {
            throw new ParseException(MONTH_ERROR_MESSAGE);
        }

        MonthOfYear month = DateUtil.convertStrToMonth(monthStr);
        return month;
    }
}
