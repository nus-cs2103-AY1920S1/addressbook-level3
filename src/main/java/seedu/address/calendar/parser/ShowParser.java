package seedu.address.calendar.parser;

import seedu.address.calendar.commands.ShowCommand;
import seedu.address.calendar.model.MonthOfYear;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ShowParser {
    private static final String FORMAT_ERROR_MESSAGE = "Incorrect month/year format.";

    private static final String MONTH_ONLY_KEY = "monthOnly";
    private static final String MONTH_ONLY_PATTERN = "(?<" + MONTH_ONLY_KEY + ">\\S+)";
    private static final String MONTH_KEY = "month";
    private static final String YEAR_KEY = "year";
    private static final String MONTH_AND_YEAR_PATTERN = "(?<" + MONTH_KEY + ">\\S+)\\s(?<" + YEAR_KEY + ">\\d{4}?)";
    /**
     * Used for initial separation of month and year (if any).
     */
    private static final Pattern MONTH_YEAR_FORMAT = Pattern.compile(MONTH_ONLY_PATTERN + "|" + MONTH_AND_YEAR_PATTERN);

    ShowCommand parse(String userInput) throws ParseException {
        final Matcher matcher = MONTH_YEAR_FORMAT.matcher(userInput.trim());

        if (!matcher.matches()) {
            throw new ParseException(FORMAT_ERROR_MESSAGE);
        }

        int year;
        String month;

        if (matcher.group(MONTH_ONLY_KEY) != null) {
            month = matcher.group(MONTH_ONLY_KEY);

            java.util.Calendar currentDate = java.util.Calendar.getInstance();
            year = currentDate.get(java.util.Calendar.YEAR);
        } else {
            month = matcher.group(MONTH_KEY);
            year = Integer.parseInt(matcher.group(YEAR_KEY));
        }

        MonthOfYear formattedMonth = new MonthParser().parse(month);

        return new ShowCommand(formattedMonth, year);
    }
}
