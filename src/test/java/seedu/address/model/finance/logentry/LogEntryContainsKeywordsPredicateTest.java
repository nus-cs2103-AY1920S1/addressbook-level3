package seedu.address.model.finance.logentry;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.LogEntryBuilder;

public class LogEntryContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        LogEntryContainsKeywordsPredicate firstPredicate =
                new LogEntryContainsKeywordsPredicate(firstPredicateKeywordList);
        LogEntryContainsKeywordsPredicate secondPredicate =
                new LogEntryContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        LogEntryContainsKeywordsPredicate firstPredicateCopy =
                new LogEntryContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different task -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_logEntryContainsKeywords_returnsTrue() {
        // One keyword
        LogEntryContainsKeywordsPredicate predicate =
                new LogEntryContainsKeywordsPredicate(Collections.singletonList("scrum"));
        assertTrue(predicate.test(new LogEntryBuilder()
                .withDescription("Fish pie scrumptious feast")
                .buildSpend()));

        // Multiple keywords, matching different fields
        predicate = new LogEntryContainsKeywordsPredicate(Arrays.asList("Fat", "Cat"));
        assertTrue(predicate.test(new LogEntryBuilder()
                .withDescription("Fattening food")
                .withCats("Cat")
                .buildSpend()));

        // Only one matching keyword
        predicate = new LogEntryContainsKeywordsPredicate(Arrays.asList("Parry", "awesome"));
        assertTrue(predicate.test(new LogEntryBuilder()
                .withDescription("Hurricanes vs Blues")
                .withCats("Westpac")
                .withTransactionMethod("EFTPOS")
                .withPerson("Parry")
                .buildIncome()));

        // Mixed-case keywords
        predicate = new LogEntryContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new LogEntryBuilder().withDescription("Alice Bob").buildSpend()));

        // Zero keywords, no specification requirement, matches all
        predicate = new LogEntryContainsKeywordsPredicate(Collections.emptyList());
        assertTrue(predicate.test(new LogEntryBuilder()
                .withDescription("Boi")
                .buildSpend()));

    }

    @Test
    public void test_logEntryDoesNotContainKeywords_returnsFalse() {
        // Non-matching keyword
        LogEntryContainsKeywordsPredicate predicate =
                new LogEntryContainsKeywordsPredicate(Arrays.asList("Katie"));
        assertFalse(predicate.test(new LogEntryBuilder()
                .withDescription("Xin's greatest gift")
                .withCats("Sullivan", "HLH")
                .withTransactionMethod("Cash")
                .withPlace("Helen Lowry")
                .buildSpend()));

        predicate = new LogEntryContainsKeywordsPredicate(Arrays.asList("YiXin"));
        assertFalse(predicate.test(new LogEntryBuilder()
                .withDescription("Rosa's and Kayla's friendship")
                .withCats("School", "MAOR123")
                .withTransactionMethod("EFTPOS")
                .withPerson("Wellington")
                .buildBorrow()));

        // Keywords match amount, transaction date but does not match
        // description, categories, transaction methods, place, person (from/to)
        predicate = new LogEntryContainsKeywordsPredicate(
                Arrays.asList("0.50", "11-07-2019"));
        assertFalse(predicate.test(new LogEntryBuilder()
                .withAmount("0.50")
                .withTransactionDate("11-07-2019")
                .withDescription("Leaving New Zealand")
                .withCats("haera", "ra")
                .withTransactionMethod("Air New Zealand")
                .withPerson("Family")
                .buildLend()));
    }
}
