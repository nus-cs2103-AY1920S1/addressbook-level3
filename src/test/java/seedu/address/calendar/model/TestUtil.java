package seedu.address.calendar.model;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.DayOfWeek;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;
import seedu.address.calendar.model.event.Commitment;
import seedu.address.calendar.model.event.Holiday;
import seedu.address.calendar.model.event.Name;
import seedu.address.calendar.model.event.SchoolBreak;
import seedu.address.calendar.model.event.Trip;

public class TestUtil {
    // two breaks that are adjacent to each other (temporally)
    public static final SchoolBreak SCHOOL_BREAK_FIRST = new SchoolBreak(new Name("First"),
            new Date(new Day(DayOfWeek.TUE, 1, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                    new Year(2065)),
            new Date(new Day(DayOfWeek.SAT, 5, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                    new Year(2065)));
    public static final SchoolBreak SCHOOL_BREAK_SECOND = new SchoolBreak(new Name("Second"),
            new Date(new Day(DayOfWeek.SUN, 6, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                    new Year(2065)),
            new Date(new Day(DayOfWeek.MON, 7, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                    new Year(2065)));
    // commitment which intersects both the school breaks above
    public static final Commitment COMMITMENT_INTERSECT_TWO_SAME =
            new Commitment(new Name("Intersect events of the same type"),
            new Date(new Day(DayOfWeek.FRI, 4, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                    new Year(2065)), new Date(new Day(DayOfWeek.SUN, 6, MonthOfYear.DECEMBER,
            new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)));

    // one day school break that is adjacent to the above
    public static final SchoolBreak SCHOOL_BREAK_THIRD = new SchoolBreak(new Name("Third"),
            new Date(new Day(DayOfWeek.TUE, 8, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                    new Year(2065)),
            new Date(new Day(DayOfWeek.TUE,
                    8, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)));


    // school break and holidays that are adjacent to each other
    public static final Holiday HOLIDAY_FIRST = new Holiday(new Name("A"), new Date(new Day(DayOfWeek.SUN, 13,
            MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)),
            new Date(new Day(DayOfWeek.TUE, 15, MonthOfYear.DECEMBER, new Year(2065)),
                    MonthOfYear.DECEMBER, new Year(2065)));
    public static final SchoolBreak SCHOOL_BREAK_FOURTH = new SchoolBreak(new Name("Fourth"),
            new Date(new Day(DayOfWeek.THU, 10, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                    new Year(2065)),
            new Date(new Day(DayOfWeek.SAT,  12, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                    new Year(2065)));

    // trip overlaps with the beginning of holiday
    public static final Holiday HOLIDAY_SECOND = new Holiday(new Name("B"), new Date(new Day(DayOfWeek.FRI, 25,
            MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)),
            new Date(new Day(DayOfWeek.SUN, 27, MonthOfYear.DECEMBER,
            new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)));
    public static final Trip TRIP_OVERLAP_BEFORE = new Trip(new Name("Overlap before"), new Date(new Day(DayOfWeek.WED,
            23, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)),
            new Date(new Day(DayOfWeek.SAT, 26, MonthOfYear.DECEMBER,
                    new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)));

    // commitment overlaps with the end of school break
    public static final Commitment COMMITMENT_OVERLAP_AFTER = new Commitment(new Name("Overlap after"),
            new Date(new Day(DayOfWeek.FRI, 27, MonthOfYear.NOVEMBER, new Year(2065)),
                    MonthOfYear.NOVEMBER, new Year(2065)), new Date(new Day(DayOfWeek.MON, 30,
            MonthOfYear.NOVEMBER, new Year(2065)), MonthOfYear.NOVEMBER, new Year(2065)));
    public static final SchoolBreak SCHOOL_BREAK_FIFTH = new SchoolBreak(new Name("Overlap after"),
            new Date(new Day(DayOfWeek.WED, 25, MonthOfYear.NOVEMBER, new Year(2065)),
                    MonthOfYear.NOVEMBER, new Year(2065)), new Date(new Day(DayOfWeek.SAT, 28,
            MonthOfYear.NOVEMBER, new Year(2065)), MonthOfYear.NOVEMBER, new Year(2065)));

    // trip intersect holiday (in the middle)
    public static final Holiday HOLIDAY_THIRD = new Holiday(new Name("C"), new Date(new Day(DayOfWeek.WED, 8,
            MonthOfYear.MAY, new Year(2069)), MonthOfYear.MAY, new Year(2069)), new Date(new Day(DayOfWeek.FRI, 24,
            MonthOfYear.MAY, new Year(2069)), MonthOfYear.MAY, new Year(2069)));
    public static final Trip TRIP_INTERSECT_BETWEEN = new Trip(new Name("Intersect between"),
            new Date(new Day(DayOfWeek.TUE, 14, MonthOfYear.MAY, new Year(2069)), MonthOfYear.MAY,
                    new Year(2069)),
            new Date(new Day(DayOfWeek.SAT, 18, MonthOfYear.MAY, new Year(2069)), MonthOfYear.MAY,
                    new Year(2069)));

    // commitments and holiday on the same day
    public static final Commitment COMMITMENT_SAME_AS_HOLIDAY = new Commitment(new Name("Same as break"),
            new Date(new Day(DayOfWeek.TUE, 15, MonthOfYear.JULY, new Year(2053)),
                    MonthOfYear.JULY, new Year(2053)), new Date(new Day(DayOfWeek.FRI, 18,
            MonthOfYear.JULY, new Year(2053)), MonthOfYear.JULY, new Year(2053)));
    public static final Holiday HOLIDAY_FOURTH = new Holiday(new Name("D"), new Date(new Day(DayOfWeek.TUE, 15,
            MonthOfYear.JULY, new Year(2053)), MonthOfYear.JULY, new Year(2053)), new Date(new Day(DayOfWeek.FRI,
            18, MonthOfYear.JULY, new Year(2053)), MonthOfYear.JULY, new Year(2053)));

    // no intersections
    public static final Trip TRIP_NO_INTERSECTION = new Trip(new Name("No intersection"),
            new Date(new Day(DayOfWeek.TUE, 11, MonthOfYear.MARCH, new Year(2053)), MonthOfYear.MARCH,
                    new Year(2053)),
            new Date(new Day(DayOfWeek.THU, 13, MonthOfYear.MARCH, new Year(2053)), MonthOfYear.MARCH,
                    new Year(2053)));
    public static final SchoolBreak SCHOOL_BREAK_SIXTH = new SchoolBreak(new Name("Sixth"),
            new Date(new Day(DayOfWeek.FRI, 14, MonthOfYear.MARCH, new Year(2053)), MonthOfYear.MARCH,
                    new Year(2053)),
            new Date(new Day(DayOfWeek.MON, 17, MonthOfYear.MARCH, new Year(2053)), MonthOfYear.MARCH,
                    new Year(2053)));

    // straddle across year
    public static final Holiday HOLIDAY_FIFTH = new Holiday(new Name("E"), new Date(new Day(DayOfWeek.MON,
            29, MonthOfYear.DECEMBER, new Year(2053)), MonthOfYear.DECEMBER, new Year(2053)),
            new Date(new Day(DayOfWeek.SAT, 3, MonthOfYear.JANUARY, new Year(2054)), MonthOfYear.JANUARY,
                    new Year(2054)));
    public static final Trip TRIP_ACROSS_YEAR = new Trip(new Name("Across year"), new Date(new Day(DayOfWeek.THU,
            1, MonthOfYear.JANUARY, new Year(2054)), MonthOfYear.JANUARY, new Year(2054)),
            new Date(new Day(DayOfWeek.SAT, 3, MonthOfYear.JANUARY, new Year(2054)), MonthOfYear.JANUARY,
                    new Year(2054)));

    // straddle across month
    public static final Holiday HOLIDAY_SIXTH = new Holiday(new Name("F"), new Date(new Day(DayOfWeek.THU,
            29, MonthOfYear.OCTOBER, new Year(2054)), MonthOfYear.OCTOBER, new Year(2054)),
            new Date(new Day(DayOfWeek.WED, 4, MonthOfYear.NOVEMBER, new Year(2054)), MonthOfYear.NOVEMBER,
                    new Year(2054)));
    public static final Commitment COMMITMENT_ACROSS_MONTH = new Commitment(new Name("Across month"),
            new Date(new Day(DayOfWeek.SAT, 31, MonthOfYear.OCTOBER, new Year(2054)),
                    MonthOfYear.OCTOBER, new Year(2054)), new Date(new Day(DayOfWeek.SUN, 1,
            MonthOfYear.NOVEMBER, new Year(2054)), MonthOfYear.NOVEMBER, new Year(2054)));
}
