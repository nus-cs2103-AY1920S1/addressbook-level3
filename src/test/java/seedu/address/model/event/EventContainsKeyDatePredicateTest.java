package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.EventBuilder;

public class EventContainsKeyDatePredicateTest {

    @Test
    public void equals() throws ParseException {
        LocalDate firstPredicateKeyDate = ParserUtil.parseAnyDate("20/12/2019");
        LocalDate secondPredicateKeyDate = ParserUtil.parseAnyDate("21/12/2019");

        EventContainsKeyDatePredicate firstPredicate = new EventContainsKeyDatePredicate(firstPredicateKeyDate);
        EventContainsKeyDatePredicate secondPredicate = new EventContainsKeyDatePredicate(secondPredicateKeyDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value / predicate -> returns true
        EventContainsKeyDatePredicate firstPredicateCopy = new EventContainsKeyDatePredicate(firstPredicateKeyDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_eventContainsKeyDate_returnsTrue() throws ParseException {
        // Event that starts on the same predicate date
        EventContainsKeyDatePredicate predicate =
                new EventContainsKeyDatePredicate(ParserUtil.parseAnyDate("20/12/2019"));
        assertTrue(predicate.test(new EventBuilder().withStartDate(ParserUtil.parseAnyDate("20/12/2019"))
                .withEndDate(ParserUtil.parseAnyDate("22/12/2019")).build()));

        // Event that ends on the same predicate date
        predicate = new EventContainsKeyDatePredicate(ParserUtil.parseAnyDate("21/12/2019"));
        assertTrue(predicate.test(new EventBuilder().withStartDate(ParserUtil.parseAnyDate("19/12/2019"))
                .withEndDate(ParserUtil.parseAnyDate("21/12/2019")).build()));

        // Event that is multiple days long with one of the event date being the predicate
        predicate = new EventContainsKeyDatePredicate(ParserUtil.parseAnyDate("22/12/2019"));
        assertTrue(predicate.test(new EventBuilder().withStartDate(ParserUtil.parseAnyDate("20/12/2019"))
                .withEndDate(ParserUtil.parseAnyDate("25/12/2019")).build()));
    }

    @Test
    public void test_eventDoesNotContainsKeyDate_returnsFalse() throws ParseException {
        // Event start date is after predicate date
        EventContainsKeyDatePredicate predicate =
                new EventContainsKeyDatePredicate(ParserUtil.parseAnyDate("20/12/2019"));
        assertFalse(predicate.test(new EventBuilder().withStartDate(ParserUtil.parseAnyDate("25/12/2019"))
                .withEndDate(ParserUtil.parseAnyDate("26/12/2019")).build()));

        // Event end date is before predicate date
        predicate = new EventContainsKeyDatePredicate(ParserUtil.parseAnyDate("20/12/2019"));
        assertFalse(predicate.test(new EventBuilder().withStartDate(ParserUtil.parseAnyDate("18/12/2019"))
                .withEndDate(ParserUtil.parseAnyDate("19/12/2019")).build()));

        // Multiple Day event that does not span through predicate date
        predicate = new EventContainsKeyDatePredicate(ParserUtil.parseAnyDate("20/12/2019"));
        assertFalse(predicate.test(new EventBuilder().withStartDate(ParserUtil.parseAnyDate("21/12/2019"))
                .withEndDate(ParserUtil.parseAnyDate("26/12/2019")).build()));
    }
}
