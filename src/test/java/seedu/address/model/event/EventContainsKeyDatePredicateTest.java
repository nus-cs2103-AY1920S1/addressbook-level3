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
import static seedu.address.testutil.TypicalEventDates.OCT_20_2019;
import static seedu.address.testutil.TypicalEventDates.OCT_21_2019;
import static seedu.address.testutil.TypicalEvents.MUSICAL;

import org.junit.jupiter.api.Test;

class EventContainsKeyDatePredicateTest {

    @Test
    void testEventContainsDatePredicateTrue() {
        EventDate eventDate;
        EventContainsKeyDatePredicate predicate;

        //On Start Date
        eventDate = OCT_10_2021;
        predicate = new EventContainsKeyDatePredicate(eventDate);
        assertTrue(predicate.test(MUSICAL));

        //On End Date
        eventDate = OCT_15_2021;
        predicate = new EventContainsKeyDatePredicate(eventDate);
        assertTrue(predicate.test(MUSICAL));

        //Within Start-End Date

        eventDate = OCT_13_2021;
        predicate = new EventContainsKeyDatePredicate(eventDate);
        assertTrue(predicate.test(MUSICAL));
    }

    @Test
    void testEventContainsDatePredicateFalse() {
        EventDate eventDate;
        EventContainsKeyDatePredicate predicate;

        //Before Start Date
        eventDate = OCT_09_2021;
        predicate = new EventContainsKeyDatePredicate(eventDate);
        assertFalse(predicate.test(MUSICAL));

        //After End Date
        eventDate = OCT_16_2021;
        predicate = new EventContainsKeyDatePredicate(eventDate);
        assertFalse(predicate.test(MUSICAL));
    }

    @Test
    void equals() {
        EventDate eventDate;
        EventContainsKeyDatePredicate predicate;

        eventDate = OCT_20_2019;
        predicate = new EventContainsKeyDatePredicate(eventDate);

        //same object --> Returns true
        assertTrue(predicate.equals(predicate));

        //same values --> Returns true
        EventContainsKeyDatePredicate predicateSame = new EventContainsKeyDatePredicate(eventDate);
        assertTrue(predicate.equals(predicateSame));

        //different types --> Returns false
        assertFalse(predicate.equals("20/10/2019"));

        //null --> Returns false
        assertFalse(predicate.equals(null));

        //different predicate with different values --> Returns false
        EventDate eventDateDiff = OCT_21_2019;
        EventContainsKeyDatePredicate predicateDiff = new EventContainsKeyDatePredicate(eventDateDiff);
        assertFalse(predicateDiff.equals(predicate));


    }
}
