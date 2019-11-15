package seedu.address.calendar.model.event;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import seedu.address.calendar.model.TestUtil;
import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.date.Day;
import seedu.address.calendar.model.date.DayOfWeek;
import seedu.address.calendar.model.date.MonthOfYear;
import seedu.address.calendar.model.date.Year;
import seedu.address.calendar.model.event.exceptions.ClashException;
import seedu.address.calendar.model.event.exceptions.DuplicateEventException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EventManagerTest {
    // intersect two events
    SchoolBreak schoolBreakFirst = TestUtil.SCHOOL_BREAK_FIRST;
    SchoolBreak schoolBreakSecond = TestUtil.SCHOOL_BREAK_SECOND;
    Commitment commitmentIntersectTwo = TestUtil.COMMITMENT_INTERSECT_TWO_SAME;

    // adjacent
    SchoolBreak schoolBreakThird = TestUtil.SCHOOL_BREAK_THIRD;

    // school breaks and holidays are adjacent to each other
    Holiday holidayFirst = TestUtil.HOLIDAY_FIRST;
    SchoolBreak schoolBreakFourth = TestUtil.SCHOOL_BREAK_FOURTH;

    // trip overlaps with the beginning
    Holiday holidaySecond = TestUtil.HOLIDAY_SECOND;
    Trip tripOverlapBefore = TestUtil.TRIP_OVERLAP_BEFORE;

    // commitment overlaps with the end of school break
    Commitment commitmentOverlapAfter = TestUtil.COMMITMENT_OVERLAP_AFTER;
    SchoolBreak schoolBreakFifth = TestUtil.SCHOOL_BREAK_FIFTH;

    // trip intersect holiday (in the middle)
    Holiday holidayThird = TestUtil.HOLIDAY_THIRD;
    Trip tripIntersectBetween = TestUtil.TRIP_INTERSECT_BETWEEN;

    // commitments and holiday on the same day
    Commitment commitmentSameAsHoliday = TestUtil.COMMITMENT_SAME_AS_HOLIDAY;
    Holiday holidayFourth = TestUtil.HOLIDAY_FOURTH;

    // no intersections
    Trip tripNoIntersection = TestUtil.TRIP_NO_INTERSECTION;
    SchoolBreak schoolBreakSixth = TestUtil.SCHOOL_BREAK_SIXTH;

    // straddle across year
    Holiday holidayFifth = TestUtil.HOLIDAY_FIFTH;
    Trip tripAcrossYear = TestUtil.TRIP_ACROSS_YEAR;

    // straddle across month
    Holiday holidaySixth = TestUtil.HOLIDAY_SIXTH;
    Commitment commitmentAcrossMonth = TestUtil.COMMITMENT_ACROSS_MONTH;

    @Test
    public void add_duplicateEvents_throwsDuplicateEventException() {
        EventManager eventManager = new EventManager();
        eventManager.add(schoolBreakFirst);
        SchoolBreak schoolBreakCopy = new SchoolBreak(new Name("First"),
                new Date(new Day(DayOfWeek.TUE, 1, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                        new Year(2065)), new Date(new Day(DayOfWeek.SAT, 5, MonthOfYear.DECEMBER,
                new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)));
        Assertions.assertThrows(DuplicateEventException.class, () -> eventManager.add(schoolBreakCopy));
        Assertions.assertThrows(DuplicateEventException.class, () -> eventManager.add(schoolBreakFirst));
    }

    @Test
    public void add_clashingEngagements_throwsClashException() {
        EventManager eventManager = new EventManager();
        eventManager.add(commitmentIntersectTwo);
        Trip trip = new Trip(new Name("Intersect events of the same type"),
                new Date(new Day(DayOfWeek.FRI, 4, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                        new Year(2065)), new Date(new Day(DayOfWeek.SUN, 6, MonthOfYear.DECEMBER,
                new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)));
        Trip partlyClashingTrip = new Trip(new Name("Intersect events of the same type"),
                new Date(new Day(DayOfWeek.FRI, 4, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                        new Year(2065)), new Date(new Day(DayOfWeek.SAT, 5, MonthOfYear.DECEMBER,
                new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)));
        Commitment partlyClashingCommitment = new Commitment(new Name("Intersect events of the same type"),
                new Date(new Day(DayOfWeek.SAT, 5, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                        new Year(2065)), new Date(new Day(DayOfWeek.SUN, 6, MonthOfYear.DECEMBER,
                new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)));

        Assertions.assertThrows(ClashException.class, () -> eventManager.add(trip));
        Assertions.assertThrows(ClashException.class, () -> eventManager.add(partlyClashingTrip));
        Assertions.assertThrows(ClashException.class, () -> eventManager.add(partlyClashingCommitment));
    }

    @Test
    public void add() {
        EventManager eventManager = new EventManager();
        eventManager.add(schoolBreakFirst);
        SchoolBreak schoolBreakCopy = new SchoolBreak(new Name("Copy"),
                new Date(new Day(DayOfWeek.TUE, 1, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                        new Year(2065)),
                new Date(new Day(DayOfWeek.SAT, 5, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                        new Year(2065)));
        Assertions.assertTrue(eventManager.add(schoolBreakCopy));
        Assertions.assertTrue(eventManager.add(commitmentIntersectTwo));
        Assertions.assertTrue(eventManager.add(tripAcrossYear));
        Assertions.assertTrue(eventManager.add(holidayFifth));
    }

    @Test
    public void remove_nonExistent_throwsNoSuchElementException() {
        EventManager eventManager = new EventManager();
        eventManager.add(TestUtil.SCHOOL_BREAK_FIRST);

        // remove something similar
        SchoolBreak schoolBreakDiff = new SchoolBreak(new Name("Hey"),
                new Date(new Day(DayOfWeek.TUE, 1, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                        new Year(2065)), new Date(new Day(DayOfWeek.SAT, 5, MonthOfYear.DECEMBER,
                new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)));
        Assertions.assertThrows(NoSuchElementException.class, () -> eventManager.remove(schoolBreakDiff));

        // remove something very different
        Trip trip = new Trip(new Name("Japan trip"), new Date(new Day(DayOfWeek.SUN, 1, MonthOfYear.DECEMBER,
                new Year(2019)), MonthOfYear.DECEMBER, new Year(2019)), new Date(new Day(DayOfWeek.WED, 1,
                MonthOfYear.JANUARY, new Year(2020)), MonthOfYear.JANUARY, new Year(2020)));
        Assertions.assertThrows(NoSuchElementException.class, () -> eventManager.remove(trip));

        // remove different event type
        Holiday holiday = new Holiday(new Name("First"),
                new Date(new Day(DayOfWeek.TUE, 1, MonthOfYear.DECEMBER, new Year(2065)), MonthOfYear.DECEMBER,
                        new Year(2065)), new Date(new Day(DayOfWeek.SAT, 5, MonthOfYear.DECEMBER,
                new Year(2065)), MonthOfYear.DECEMBER, new Year(2065)));
        Assertions.assertThrows(NoSuchElementException.class, () -> eventManager.remove(holiday));
    }

    @Test
    public void remove() {
        EventManager eventManager = new EventManager();
        addAll(eventManager);

        Assertions.assertTrue(eventManager.remove(schoolBreakSixth));
        Assertions.assertTrue(eventManager.remove(schoolBreakThird));
        Assertions.assertTrue(eventManager.remove(schoolBreakFourth));
        Assertions.assertTrue(eventManager.remove(commitmentAcrossMonth));
        Assertions.assertTrue(eventManager.remove(commitmentOverlapAfter));
        Assertions.assertTrue(eventManager.remove(holidayThird));
        Assertions.assertTrue(eventManager.remove(holidaySixth));
        Assertions.assertTrue(eventManager.remove(holidaySecond));
        Assertions.assertTrue(eventManager.remove(tripOverlapBefore));
        Assertions.assertTrue(eventManager.remove(tripIntersectBetween));
        Assertions.assertTrue(eventManager.remove(holidayFourth));
        Assertions.assertTrue(eventManager.remove(holidayFirst));
        Assertions.assertTrue(eventManager.remove(schoolBreakFifth));
        Assertions.assertTrue(eventManager.remove(schoolBreakSecond));
        Assertions.assertTrue(eventManager.remove(schoolBreakFirst));
        Assertions.assertTrue(eventManager.remove(tripAcrossYear));
        Assertions.assertTrue(eventManager.remove(tripNoIntersection));
        Assertions.assertTrue(eventManager.remove(commitmentSameAsHoliday));
        Assertions.assertTrue(eventManager.remove(commitmentIntersectTwo));
        Assertions.assertTrue(eventManager.remove(holidayFifth));
    }

    @Test
    public void suggest() {
        EventManager eventManager = new EventManager();
        Date startDate = new Date(new Day(DayOfWeek.SAT, 1, MonthOfYear.JANUARY, new Year(2050)),
                MonthOfYear.JANUARY, new Year(2050)); // start with 2050
        Date endDate = new Date(new Day(DayOfWeek.WED, 31, MonthOfYear.DECEMBER, new Year(2070)),
                MonthOfYear.DECEMBER, new Year(2070)); // end with 2070
        assertEquals("", eventManager.suggest(new EventQuery(startDate, endDate)));

        addAll(eventManager);

        String expectedOutput = "From Fri, 14 March 2053 to Mon, 17 March 2053\n" +
                "From Mon, 29 December 2053 to Wed, 31 December 2053\n" +
                "From Thu, 29 October 2054 to Fri, 30 October 2054\n" +
                "From Mon, 2 November 2054 to Wed, 4 November 2054\n" +
                "From Wed, 25 November 2065 to Thu, 26 November 2065\n" +
                "From Tue, 1 December 2065 to Thu, 3 December 2065\n" +
                "From Mon, 7 December 2065 to Tue, 8 December 2065\n" +
                "From Thu, 10 December 2065 to Tue, 15 December 2065\n" +
                "On Sun, 27 December 2065\n" +
                "From Wed, 8 May 2069 to Mon, 13 May 2069\n" +
                "From Sun, 19 May 2069 to Fri, 24 May 2069";
        assertEquals(expectedOutput, eventManager.suggest(new EventQuery(startDate, endDate)));

        // with deletion
        eventManager.remove(holidayThird);
        String expectedOutputAfterDeletion = "From Fri, 14 March 2053 to Mon, 17 March 2053\n" +
                "From Mon, 29 December 2053 to Wed, 31 December 2053\n" +
                "From Thu, 29 October 2054 to Fri, 30 October 2054\n" +
                "From Mon, 2 November 2054 to Wed, 4 November 2054\n" +
                "From Wed, 25 November 2065 to Thu, 26 November 2065\n" +
                "From Tue, 1 December 2065 to Thu, 3 December 2065\n" +
                "From Mon, 7 December 2065 to Tue, 8 December 2065\n" +
                "From Thu, 10 December 2065 to Tue, 15 December 2065\n" +
                "On Sun, 27 December 2065";
        assertEquals(expectedOutputAfterDeletion, eventManager.suggest(new EventQuery(startDate, endDate)));

        // add it back again
        eventManager.add(holidayThird);
        assertEquals(expectedOutput, eventManager.suggest(new EventQuery(startDate, endDate)));

        // try it with overlapping holidays
        Holiday overlapHoliday = new Holiday(new Name("test"), new Date(new Day(DayOfWeek.THU,
                29, MonthOfYear.OCTOBER, new Year(2054)), MonthOfYear.OCTOBER, new Year(2054)),
                new Date(new Day(DayOfWeek.WED, 4, MonthOfYear.NOVEMBER, new Year(2054)), MonthOfYear.NOVEMBER,
                        new Year(2054)));
        eventManager.add(overlapHoliday);
        assertEquals(expectedOutput, eventManager.suggest(new EventQuery(startDate, endDate)));

        // try it with clashing events
        Commitment clashCommitment = new Commitment(new Name("Clash"),
                new Date(new Day(DayOfWeek.SAT, 31, MonthOfYear.OCTOBER, new Year(2054)),
                        MonthOfYear.OCTOBER, new Year(2054)), new Date(new Day(DayOfWeek.SUN, 1,
                MonthOfYear.NOVEMBER, new Year(2054)), MonthOfYear.NOVEMBER, new Year(2054)));
        eventManager.addIgnoreClash(clashCommitment);
        assertEquals(expectedOutput, eventManager.suggest(new EventQuery(startDate, endDate)));

        // remove clash
        eventManager.remove(clashCommitment);
        assertEquals(expectedOutput, eventManager.suggest(new EventQuery(startDate, endDate)));

        // remove those with same date
        eventManager.remove(commitmentAcrossMonth);
        String expectedOutputAfterDeletionCommitment = "From Fri, 14 March 2053 to Mon, 17 March 2053\n" +
                "From Mon, 29 December 2053 to Wed, 31 December 2053\n" +
                "From Thu, 29 October 2054 to Wed, 4 November 2054\n" +
                "From Wed, 25 November 2065 to Thu, 26 November 2065\n" +
                "From Tue, 1 December 2065 to Thu, 3 December 2065\n" +
                "From Mon, 7 December 2065 to Tue, 8 December 2065\n" +
                "From Thu, 10 December 2065 to Tue, 15 December 2065\n" +
                "On Sun, 27 December 2065\n" +
                "From Wed, 8 May 2069 to Mon, 13 May 2069\n" +
                "From Sun, 19 May 2069 to Fri, 24 May 2069";
        assertEquals(expectedOutputAfterDeletionCommitment, eventManager.suggest(new EventQuery(startDate, endDate)));

        // when there are no events at specified period
        Date startDateExtreme = new Date(new Day(DayOfWeek.SUN, 1, MonthOfYear.JANUARY, new Year(2012)),
                MonthOfYear.JANUARY, new Year(2012));
        Date endDateExtreme = new Date(new Day(DayOfWeek.SAT, 9, MonthOfYear.NOVEMBER, new Year(2019)),
                MonthOfYear.NOVEMBER, new Year(2019));
        assertEquals("", eventManager.suggest(new EventQuery(startDateExtreme, endDateExtreme)));
        assertEquals("", eventManager.suggest(new EventQuery(startDateExtreme, startDateExtreme)));
    }

    @Test
    public void suggest_withMinPeriod_invalidMinPeriod_throwsIllegalArgumentException() {
        EventManager eventManager = new EventManager();
        Date startDate = new Date(new Day(DayOfWeek.SAT, 1, MonthOfYear.JANUARY, new Year(2050)),
                MonthOfYear.JANUARY, new Year(2050)); // start with 2050
        Date endDate = new Date(new Day(DayOfWeek.WED, 31, MonthOfYear.DECEMBER, new Year(2070)),
                MonthOfYear.DECEMBER, new Year(2070)); // end with 2070
        EventQuery eventQuery = new EventQuery(startDate, endDate);
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventManager.suggest(eventQuery, Integer.MIN_VALUE));
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventManager.suggest(eventQuery, -1));
        Assertions.assertThrows(IllegalArgumentException.class, () -> eventManager.suggest(eventQuery, 0));
    }

    @Test
    public void suggest_withMinPeriod() {
        EventManager eventManager = new EventManager();
        Date startDate = new Date(new Day(DayOfWeek.SAT, 1, MonthOfYear.JANUARY, new Year(2050)),
                MonthOfYear.JANUARY, new Year(2050)); // start with 2050
        Date endDate = new Date(new Day(DayOfWeek.WED, 31, MonthOfYear.DECEMBER, new Year(2070)),
                MonthOfYear.DECEMBER, new Year(2070)); // end with 2070
        assertEquals("", eventManager.suggest(new EventQuery(startDate, endDate)));

        addAll(eventManager);

        String expectedOutput = "From Fri, 14 March 2053 to Mon, 17 March 2053\n" +
                "From Mon, 29 December 2053 to Wed, 31 December 2053\n" +
                "From Thu, 29 October 2054 to Fri, 30 October 2054\n" +
                "From Mon, 2 November 2054 to Wed, 4 November 2054\n" +
                "From Wed, 25 November 2065 to Thu, 26 November 2065\n" +
                "From Tue, 1 December 2065 to Thu, 3 December 2065\n" +
                "From Mon, 7 December 2065 to Tue, 8 December 2065\n" +
                "From Thu, 10 December 2065 to Tue, 15 December 2065\n" +
                "On Sun, 27 December 2065\n" +
                "From Wed, 8 May 2069 to Mon, 13 May 2069\n" +
                "From Sun, 19 May 2069 to Fri, 24 May 2069";
        assertEquals(expectedOutput, eventManager.suggest(new EventQuery(startDate, endDate), 1));

        String expectedOutputMinThree = "From Fri, 14 March 2053 to Mon, 17 March 2053\n" +
                "From Mon, 29 December 2053 to Wed, 31 December 2053\n" +
                "From Mon, 2 November 2054 to Wed, 4 November 2054\n" +
                "From Tue, 1 December 2065 to Thu, 3 December 2065\n" +
                "From Thu, 10 December 2065 to Tue, 15 December 2065\n" +
                "From Wed, 8 May 2069 to Mon, 13 May 2069\n" +
                "From Sun, 19 May 2069 to Fri, 24 May 2069";
        assertEquals(expectedOutputMinThree, eventManager.suggest(new EventQuery(startDate, endDate), 3));

        String expectedOutputMinFour = "From Fri, 14 March 2053 to Mon, 17 March 2053\n" +
                "From Thu, 10 December 2065 to Tue, 15 December 2065\n" +
                "From Wed, 8 May 2069 to Mon, 13 May 2069\n" +
                "From Sun, 19 May 2069 to Fri, 24 May 2069";
        assertEquals(expectedOutputMinFour, eventManager.suggest(new EventQuery(startDate, endDate), 4));

        assertEquals("", eventManager.suggest(new EventQuery(startDate, endDate), Integer.MAX_VALUE));
    }

    @Test
    public void getEvents() {
        EventManager eventManager = new EventManager();
        Date startDate = new Date(new Day(DayOfWeek.SAT, 1, MonthOfYear.JANUARY, new Year(2050)),
                MonthOfYear.JANUARY, new Year(2050)); // start with 2050
        Date endDate = new Date(new Day(DayOfWeek.WED, 31, MonthOfYear.DECEMBER, new Year(2070)),
                MonthOfYear.DECEMBER, new Year(2070)); // end with 2070
        EventQuery eventQuery = new EventQuery(startDate, endDate);
        addAll(eventManager);

        List<Event> expected = new ArrayList<>();
        addAll(expected);
        long numMatches = eventManager.getEvents(eventQuery)
                .filter(event -> expected.stream().anyMatch(expectedEvent -> expectedEvent.equals(event)))
                .count();
        assertEquals(expected.size(), (int) numMatches);
    }

    /**
     * Adds all events from {@code TestUtil} to the specified event manager.
     * @param eventManager The specified event manager
     */
    private void addAll(EventManager eventManager) {
        eventManager.add(schoolBreakFirst);
        eventManager.add(schoolBreakSecond);
        eventManager.add(commitmentIntersectTwo);
        eventManager.add(holidayFifth);
        eventManager.add(holidayFirst);
        eventManager.add(tripAcrossYear);
        eventManager.add(tripIntersectBetween);
        eventManager.add(holidayFourth);
        eventManager.add(schoolBreakFifth);
        eventManager.add(commitmentAcrossMonth);
        eventManager.add(holidaySecond);
        eventManager.add(commitmentSameAsHoliday);
        eventManager.add(schoolBreakFourth);
        eventManager.add(schoolBreakThird);
        eventManager.add(tripOverlapBefore);
        eventManager.add(holidaySixth);
        eventManager.add(holidayThird);
        eventManager.add(commitmentOverlapAfter);
        eventManager.add(schoolBreakSixth);
        eventManager.add(tripNoIntersection);
    }

    /**
     * Adds all events from {@code TestUtil} to the specified list.
     *
     * @param events The specified list
     */
    private void addAll(List<Event> events) {
        events.add(schoolBreakFirst);
        events.add(schoolBreakSecond);
        events.add(commitmentIntersectTwo);
        events.add(holidayFifth);
        events.add(holidayFirst);
        events.add(tripAcrossYear);
        events.add(tripIntersectBetween);
        events.add(holidayFourth);
        events.add(schoolBreakFifth);
        events.add(commitmentAcrossMonth);
        events.add(holidaySecond);
        events.add(commitmentSameAsHoliday);
        events.add(schoolBreakFourth);
        events.add(schoolBreakThird);
        events.add(tripOverlapBefore);
        events.add(holidaySixth);
        events.add(holidayThird);
        events.add(commitmentOverlapAfter);
        events.add(schoolBreakSixth);
        events.add(tripNoIntersection);
    }
}
