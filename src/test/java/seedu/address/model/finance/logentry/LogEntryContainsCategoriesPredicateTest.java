package seedu.address.model.finance.logentry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LogEntryBuilder;

public class LogEntryContainsCategoriesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateCategoryList = Collections.singletonList("first");
        List<String> secondPredicateCategoryList = Arrays.asList("first", "second");

        LogEntryContainsCategoriesPredicate firstPredicate =
                new LogEntryContainsCategoriesPredicate(firstPredicateCategoryList);
        LogEntryContainsCategoriesPredicate secondPredicate =
                new LogEntryContainsCategoriesPredicate(secondPredicateCategoryList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LogEntryContainsCategoriesPredicate firstPredicateCopy =
               new LogEntryContainsCategoriesPredicate(firstPredicateCategoryList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_logEntryContainsCategories_returnsTrue() {
        // One keyword
        LogEntryContainsCategoriesPredicate predicate =
                new LogEntryContainsCategoriesPredicate(Collections.singletonList("CATS"));
        assertTrue(predicate.test(new LogEntryBuilder()
                .withCats("CATS", "BATS")
                .buildSpend()));

        // Multiple keywords
        predicate = new LogEntryContainsCategoriesPredicate(Arrays.asList("RANGITOTO", "AUCKLAND"));
        assertTrue(predicate.test(new LogEntryBuilder()
                .withCats("RANGITOTO")
                .buildBorrow()));

        // Mixed-case keywords
        predicate = new LogEntryContainsCategoriesPredicate(Arrays.asList("WLG", "CcH", "Akl"));
        assertTrue(predicate.test(new LogEntryBuilder()
                .withCats("wlg")
                .buildIncome()));

        // Zero keywords, no requirements specified, matches all log entries
        predicate = new LogEntryContainsCategoriesPredicate(Collections.emptyList());
        assertTrue(predicate.test(new LogEntryBuilder()
                .withCats("BATS")
                .buildSpend()));
    }

    @Test
    public void test_logEntryDoesNotContainCategories_returnsFalse() {

        // Non-matching keyword
        LogEntryContainsCategoriesPredicate predicate = new LogEntryContainsCategoriesPredicate(Arrays.asList("VUWSA"));
        assertFalse(predicate.test(new LogEntryBuilder()
                .withCats("VUW")
                .buildIncome()));

        // Keywords match amount, transaction date, description, transaction methods,
        // place, person (from/to) but not any categories
        predicate =
                new LogEntryContainsCategoriesPredicate(
                        Arrays.asList("sister", "300", "10-01-2019", "Visa", "Mastercard"));
        assertFalse(predicate.test(new LogEntryBuilder()
                .withAmount("300")
                .withTransactionDate("10-01-2019")
                .withDescription("Visa")
                .withCats("kia", "ora")
                .withTransactionMethod("Mastercard")
                .withPerson("sister")
                .buildBorrow()));
    }
}
