/*
@@author shihaoyap
 */

package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.testutil.EventBuilder;

public class EventContainsKeyYearMonthPredicateTest {

    @Test
    public void equals() throws ParseException {
        YearMonth firstPredicateKeyYearMonth = ParserUtil.parseYearMonth("11/2019");
        YearMonth secondPredicateKeyYearMonth = ParserUtil.parseYearMonth("12/2019");

        EventContainsKeyYearMonthPredicate firstPredicate =
                new EventContainsKeyYearMonthPredicate(firstPredicateKeyYearMonth);
        EventContainsKeyYearMonthPredicate secondPredicate =
                new EventContainsKeyYearMonthPredicate(secondPredicateKeyYearMonth);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value / predicate -> returns true
        EventContainsKeyYearMonthPredicate firstPredicateCopy =
                new EventContainsKeyYearMonthPredicate(firstPredicateKeyYearMonth);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_eventContainsKeyYearMonth_returnsTrue() throws ParseException {
        // Event that starts on the first day of the month
        EventContainsKeyYearMonthPredicate predicate =
                new EventContainsKeyYearMonthPredicate(ParserUtil.parseYearMonth("11/2019"));
        assertTrue(predicate.test(new EventBuilder().withStartDate(ParserUtil.parseAnyDate("01/11/2019"))
                .withEndDate(ParserUtil.parseAnyDate("05/12/2019")).build()));

        // Event that ends on the last day of the month
        predicate = new EventContainsKeyYearMonthPredicate(ParserUtil.parseYearMonth("11/2019"));
        assertTrue(predicate.test(new EventBuilder().withStartDate(ParserUtil.parseAnyDate("01/11/2019"))
                .withEndDate(ParserUtil.parseAnyDate("30/11/2019")).build()));

        // Event that spans through multiple month
        predicate = new EventContainsKeyYearMonthPredicate(ParserUtil.parseYearMonth("11/2019"));
        assertTrue(predicate.test(new EventBuilder().withStartDate(ParserUtil.parseAnyDate("30/10/2019"))
                .withEndDate(ParserUtil.parseAnyDate("05/12/2019")).build()));
    }

    @Test
    public void test_eventDoesNotContainsKeyYearMonth_returnsFalse() throws ParseException {
        // Event start date is after the predicate Year Month
        EventContainsKeyYearMonthPredicate predicate =
                new EventContainsKeyYearMonthPredicate(ParserUtil.parseYearMonth("11/2019"));
        assertFalse(predicate.test(new EventBuilder().withStartDate(ParserUtil.parseAnyDate("01/12/2019"))
                .withEndDate(ParserUtil.parseAnyDate("05/12/2019")).build()));

        // Event end date is before predicate Year Month
        predicate = new EventContainsKeyYearMonthPredicate(ParserUtil.parseYearMonth("12/2019"));
        assertFalse(predicate.test(new EventBuilder().withStartDate(ParserUtil.parseAnyDate("01/11/2019"))
                .withEndDate(ParserUtil.parseAnyDate("30/11/2019")).build()));

        // Multiple Day event that does not span through predicate Year Month
        predicate = new EventContainsKeyYearMonthPredicate(ParserUtil.parseYearMonth("12/2019"));
        assertFalse(predicate.test(new EventBuilder().withStartDate(ParserUtil.parseAnyDate("20/10/2019"))
                .withEndDate(ParserUtil.parseAnyDate("12/11/2019")).build()));
    }
}
