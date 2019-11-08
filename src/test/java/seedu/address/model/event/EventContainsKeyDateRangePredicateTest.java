/*
@@author shihaoyap
 */

package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalEventDates.OCT_09_2021;
import static seedu.address.testutil.TypicalEventDates.OCT_10_2021;
import static seedu.address.testutil.TypicalEventDates.OCT_13_2021;
import static seedu.address.testutil.TypicalEventDates.OCT_15_2021;
import static seedu.address.testutil.TypicalEventDates.OCT_16_2021;
import static seedu.address.testutil.TypicalEventDates.OCT_21_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_23_2019;
import static seedu.address.testutil.TypicalEvents.MUSICAL;

import org.junit.jupiter.api.Test;

class EventContainsKeyDateRangePredicateTest {

    @Test
    void testEventOutOfRange() {
        EventContainsKeyDateRangePredicate predicate;
        Event event = MUSICAL;

        //Event Out of Start Range
        predicate = new EventContainsKeyDateRangePredicate(OCT_13_2021, OCT_15_2021);
        assertFalse(predicate.test(event));

        //Event Out of End Range
        predicate = new EventContainsKeyDateRangePredicate(OCT_10_2021, OCT_13_2021);
        assertFalse(predicate.test(event));

        //Event Out of Start and End Date Range
        predicate = new EventContainsKeyDateRangePredicate(OCT_23_2019, OCT_09_2021);
        assertFalse(predicate.test(event));
    }

    @Test
    void testEventInRange() {
        EventContainsKeyDateRangePredicate predicate;
        Event event = MUSICAL;

        //Event Start and End Dates within Range
        predicate = new EventContainsKeyDateRangePredicate(OCT_09_2021, OCT_16_2021);
        assertTrue(predicate.test(event));

        //Event Start Date on Start Range
        predicate = new EventContainsKeyDateRangePredicate(OCT_10_2021, OCT_16_2021);
        assertTrue(predicate.test(event));

        //Event End DAte on End Range
        predicate = new EventContainsKeyDateRangePredicate(OCT_09_2021, OCT_15_2021);
        assertTrue(predicate.test(event));
    }

    @Test
    void equals() {
        EventDate startEventDate = OCT_10_2021;
        EventDate endEventDate = OCT_15_2021;
        EventContainsKeyDateRangePredicate predicate =
                new EventContainsKeyDateRangePredicate(startEventDate, endEventDate);

        //same object --> Returns true
        assertTrue(predicate.equals(predicate));

        //same values --> Returns true
        EventContainsKeyDateRangePredicate predicateSame =
                new EventContainsKeyDateRangePredicate(OCT_10_2021, OCT_15_2021);
        assertTrue(predicate.equals(predicateSame));

        //different types --> Returns false
        assertFalse(predicate.equals("10/10/2021-15/10/2021"));

        //null --> Returns false
        assertFalse(predicate.equals(null));

        //different predicate with different values --> Returns false
        EventDate eventStartDateDiff = OCT_21_2019;
        EventDate eventEndDateDiff = OCT_23_2019;
        EventContainsKeyDateRangePredicate predicateDiff =
                new EventContainsKeyDateRangePredicate(eventStartDateDiff, eventEndDateDiff);
        assertFalse(predicateDiff.equals(predicate));
    }
}
