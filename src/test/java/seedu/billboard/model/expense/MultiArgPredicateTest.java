package seedu.billboard.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.billboard.testutil.ExpenseBuilder;

public class MultiArgPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        Amount firstPredicateLowerAmtLimit = new Amount("2");
        Amount secondPredicateLowerAmtLimit = new Amount("3");
        Amount firstPredicateUpperAmtLimit = new Amount("4");
        Amount secondPredicateUpperAmtLimit = new Amount("5");

        CreatedDateTime firstPredicateStartDate = new CreatedDateTime("1/1/2019");
        CreatedDateTime secondPredicateStartDate = new CreatedDateTime("2/2/2019");
        CreatedDateTime firstPredicateEndDate = new CreatedDateTime("1/1/2019");
        CreatedDateTime secondPredicateEndDate = new CreatedDateTime("2/2/2019");

        MultiArgPredicate firstPredicate = new MultiArgPredicate();
        firstPredicate.setKeywords(firstPredicateKeywordList);
        firstPredicate.setDateRange(firstPredicateStartDate, firstPredicateEndDate);
        firstPredicate.setAmtRange(firstPredicateLowerAmtLimit, firstPredicateUpperAmtLimit);

        MultiArgPredicate secondPredicate = new MultiArgPredicate();
        secondPredicate.setKeywords(secondPredicateKeywordList);
        secondPredicate.setDateRange(secondPredicateStartDate, secondPredicateEndDate);
        secondPredicate.setAmtRange(secondPredicateLowerAmtLimit, secondPredicateUpperAmtLimit);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MultiArgPredicate firstPredicateCopy = new MultiArgPredicate();
        firstPredicateCopy.setKeywords(firstPredicateKeywordList);
        firstPredicateCopy.setDateRange(firstPredicateStartDate, firstPredicateEndDate);
        firstPredicateCopy.setAmtRange(firstPredicateLowerAmtLimit, firstPredicateUpperAmtLimit);

        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different expense -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_argumentsWithinLimit_returnsTrue() {
        // 3 Limits
        MultiArgPredicate predicate = new MultiArgPredicate();
        predicate.setKeywords(Arrays.asList("book"));
        predicate.setDateRange(new CreatedDateTime("1/8/2019"), new CreatedDateTime("20/8/2019"));
        predicate.setAmtRange(new Amount("2"), new Amount("20"));

        assertTrue(predicate.test(new ExpenseBuilder().withDescription("buy book").withCreatedDateTime("3/8/2019")
                .withAmount("10").build()));

        // 2 Limits
        predicate = new MultiArgPredicate();
        predicate.setKeywords(Arrays.asList("book"));
        predicate.setDateRange(new CreatedDateTime("1/8/2019"), new CreatedDateTime("20/8/2019"));

        assertTrue(predicate.test(new ExpenseBuilder().withDescription("buy book").withCreatedDateTime("2/8/2019")
                .build()));

        predicate = new MultiArgPredicate();
        predicate.setKeywords(Arrays.asList("book"));
        predicate.setAmtRange(new Amount("2"), new Amount("20"));

        assertTrue(predicate.test(new ExpenseBuilder().withDescription("buy book").withAmount("18").build()));

        predicate = new MultiArgPredicate();
        predicate.setDateRange(new CreatedDateTime("1/8/2019"), new CreatedDateTime("20/8/2019"));
        predicate.setAmtRange(new Amount("2"), new Amount("20"));

        assertTrue(predicate.test(new ExpenseBuilder().withCreatedDateTime("3/8/2019").withAmount("18").build()));

        // 1 Limit
        predicate = new MultiArgPredicate();
        predicate.setKeywords(Arrays.asList("book"));
        assertTrue(predicate.test(new ExpenseBuilder().withDescription("buy book").build()));

        predicate = new MultiArgPredicate();
        predicate.setDateRange(new CreatedDateTime("1/8/2019"), new CreatedDateTime("20/8/2019"));
        assertTrue(predicate.test(new ExpenseBuilder().withCreatedDateTime("6/8/2019").build()));

        predicate = new MultiArgPredicate();
        predicate.setAmtRange(new Amount("2"), new Amount("20"));
        assertTrue(predicate.test(new ExpenseBuilder().withAmount("18").build()));
    }

    @Test
    public void test_argumentsNotWithinLimit_returnsFalse() {
        // 3 conditions
        MultiArgPredicate predicate = new MultiArgPredicate();
        predicate.setKeywords(Arrays.asList("book"));
        predicate.setDateRange(new CreatedDateTime("1/8/2019"), new CreatedDateTime("20/8/2019"));
        predicate.setAmtRange(new Amount("2"), new Amount("20"));

        // 3 conditions, none met
        assertFalse(predicate.test(new ExpenseBuilder().build()));

        // 3 conditions, 1 met
        assertFalse(predicate.test(new ExpenseBuilder().withDescription("buy book").build()));
        assertFalse(predicate.test(new ExpenseBuilder().withCreatedDateTime("2/8/2019").build()));
        assertFalse(predicate.test(new ExpenseBuilder().withAmount("6").build()));

        // 3 conditions, 2 met
        assertFalse(predicate.test(new ExpenseBuilder().withDescription("buy book").withCreatedDateTime("2/8/2019")
                .build()));
        assertFalse(predicate.test(new ExpenseBuilder().withCreatedDateTime("2/8/2019").withAmount("15").build()));
        assertFalse(predicate.test(new ExpenseBuilder().withCreatedDateTime("5/8/2011").withAmount("10").build()));

        // 2 conditions
        predicate = new MultiArgPredicate();
        predicate.setKeywords(Arrays.asList("book"));
        predicate.setDateRange(new CreatedDateTime("1/8/2019"), new CreatedDateTime("20/8/2019"));

        // 2 conditions, 1 met
        assertFalse(predicate.test(new ExpenseBuilder().withDescription("buy book").build()));
        assertFalse(predicate.test(new ExpenseBuilder().withCreatedDateTime("2/8/2019").build()));

        // 2 conditions, none met
        assertFalse(predicate.test(new ExpenseBuilder().build()));

        // 1 condition
        predicate = new MultiArgPredicate();
        predicate.setKeywords(Arrays.asList("book"));

        // 1 condition, none met
        assertFalse(predicate.test(new ExpenseBuilder().build()));
    }
}
