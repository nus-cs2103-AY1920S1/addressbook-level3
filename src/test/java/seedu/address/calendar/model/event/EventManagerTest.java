package seedu.address.calendar.model.event;

import org.junit.jupiter.api.Test;
import seedu.address.calendar.model.TestUtil;

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
    public void allEvents() {


    }
}
