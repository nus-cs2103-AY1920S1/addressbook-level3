package seedu.address.calendar.parser;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;

import java.util.Optional;

public class DateParser {
    static Date parseStartDate(ArgumentMultimap argMultimap, Prefix monthPrefix, Prefix yearPrefix,
                               Prefix dayPrefix) throws ParseException {
        Optional<MonthOfYear> startMonth = new MonthParser().parse(argMultimap.getValue(monthPrefix));
        Optional<Year> startYear = new YearParser().parse(argMultimap.getValue(yearPrefix));
        Optional<Day> startDay = new DayParser().parse(argMultimap.getValue(dayPrefix),
                startMonth, startYear);
        // assumptions: if no start month/year specified it is the current month/year
        return new Date(startDay, startMonth, startYear);
    }

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
                .orElse(startDateDay);

        // assumptions: if nothing is specified, it will be the same as those of the start date
        return new Date(endDay, endMonth, endYear);
    }
}
