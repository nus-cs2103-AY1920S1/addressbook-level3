package seedu.address.calendar.logic.parser;

import java.util.Optional;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses the date.
 */
public class DateParser {
    /**
     * Parses the start date.
     *
     * @param argMultimap A map containing user input
     * @param monthPrefix The month prefix
     * @param yearPrefix The year prefix
     * @param dayPrefix The day prefix
     * @return The required date
     * @throws ParseException if the input cannot be parsed successfully
     */
    static Date parseStartDate(ArgumentMultimap argMultimap, Prefix monthPrefix, Prefix yearPrefix,
                               Prefix dayPrefix) throws ParseException {
        Optional<MonthOfYear> startMonth = new MonthParser().parse(argMultimap.getValue(monthPrefix));
        Optional<Year> startYear = new YearParser().parse(argMultimap.getValue(yearPrefix));
        Optional<Day> startDay = new DayParser().parse(argMultimap.getValue(dayPrefix),
                startMonth, startYear);
        // assumptions: if no start month/year specified it is the current month/year
        return new Date(startDay, startMonth, startYear);
    }

    /**
     * Parses the end date.
     *
     * @param argMultimap A map containing user input
     * @param startDate The start date of the event
     * @param monthPrefix The month prefix
     * @param yearPrefix The year prefix
     * @param dayPrefix The day prefix
     * @return  The required date
     * @throws ParseException if the input cannot be parsed successfully
     */
    static Date parseEndDate(ArgumentMultimap argMultimap, Date startDate, Prefix monthPrefix, Prefix yearPrefix,
                             Prefix dayPrefix) throws ParseException {
        Day startDateDay = startDate.getDay();
        MonthOfYear startDateMonth = startDate.getMonth();
        Year startDateYear = startDate.getYear();

        MonthOfYear endMonth = new MonthParser().parse(argMultimap.getValue(monthPrefix))
                .orElse(startDateMonth);
        Year endYear = new YearParser().parse(argMultimap.getValue(yearPrefix))
                .orElse(startDateYear);
        Day endDay = new DayParser().parse(argMultimap.getValue(dayPrefix), Optional.of(endMonth),
                Optional.of(endYear))
                .orElse(new DayParser().parse(startDateDay, Optional.of(endMonth), Optional.of(endYear)));

        // assumptions: if nothing is specified, it will be the same as those of the start date
        return new Date(endDay, endMonth, endYear);
    }
}
