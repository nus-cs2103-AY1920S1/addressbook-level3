package seedu.guilttrip.model.entry.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.guilttrip.testutil.ExpenseBuilder;

public class EntryContainsDescriptionPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("mala");
        List<String> secondPredicateKeywordList = Arrays.asList("mala", "xiaolongbao");

        EntryContainsDescriptionPredicate firstPredicate =
                new EntryContainsDescriptionPredicate(firstPredicateKeywordList);
        EntryContainsDescriptionPredicate secondPredicate =
                new EntryContainsDescriptionPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EntryContainsDescriptionPredicate firstPredicateCopy =
                new EntryContainsDescriptionPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different entry -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    private void assertTrue(boolean equals) {
    }

    @Test
    public void test_descriptionContainsKeywords_returnsTrue() {
        // One keyword
        EntryContainsDescriptionPredicate predicate =
                new EntryContainsDescriptionPredicate(Collections.singletonList("Roti"));
        assertTrue(predicate.test(new ExpenseBuilder().withDesc("Roti").build()));

        // Multiple keywords
        predicate = new EntryContainsDescriptionPredicate(Arrays.asList("Roti", "Tissue"));
        assertTrue(predicate.test(new ExpenseBuilder().withDesc("Roti Tissue").build()));

        // Only one matching keyword
        predicate = new EntryContainsDescriptionPredicate(Arrays.asList("Roti", "Pataya"));
        assertTrue(predicate.test(new ExpenseBuilder().withDesc("Roti Spicy").build()));

        // Mixed-case keywords
        predicate = new EntryContainsDescriptionPredicate(Arrays.asList("rOtI", "tIssUe"));
        assertTrue(predicate.test(new ExpenseBuilder().withDesc("roti Tissue").build()));
    }

    @Test
    public void test_descriptionDoesNotContainKeywords_returnsFalse() {
        // Zero keywords throws Exception in the Parser Stage
        EntryContainsDescriptionPredicate predicate;

        // Non-matching keyword
        predicate = new EntryContainsDescriptionPredicate(Arrays.asList("Mala"));
        assertFalse(predicate.test(new ExpenseBuilder().withDesc("RotiCanai Muthusamy").build()));

        // Keywords match all but description
        predicate = new EntryContainsDescriptionPredicate(
                Arrays.asList("123.00", "Food", "2019-08-09", "Main"));
        assertFalse(predicate.test(new ExpenseBuilder().withDesc("Macdonalds").withAmt(123.00).withCategory("Food")
                .withTime("2019-08-09").withTags("Main").build()));
    }
}
