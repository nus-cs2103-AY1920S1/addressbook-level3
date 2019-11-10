package seedu.address.model.phone.predicates;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.phone.Capacity;
import seedu.address.testutil.PhoneBuilder;

public class PhoneContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        PhoneContainsKeywordsPredicate firstPredicate =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        PhoneContainsKeywordsPredicate secondPredicate =
                new PhoneContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneContainsKeywordsPredicate firstPredicateCopy =
                new PhoneContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword that matches phone name
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("iPhone"));
        assertTrue(predicate.test(
                new PhoneBuilder().withName("iPhone").build()));

        //one keyword that matches phone capacity
        predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("128"));
        assertTrue(predicate.test(
                new PhoneBuilder().withCapacity(Capacity.SIZE_128GB).build()));


        //one keyword that matches colour
        predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("Purple"));
        assertTrue(predicate.test(
                new PhoneBuilder().withColour("Purple").build()));


        //one keyword that matches identity number
        predicate =
                new PhoneContainsKeywordsPredicate(Collections.singletonList("123456"));
        assertTrue(predicate.test(
                new PhoneBuilder().withIdentityNumber("123456789123456").build()));

        // Multiple keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("iPhone", "11"));
        assertTrue(predicate.test(
                new PhoneBuilder().withName("iPhone 11").build()));


        // Mixed-case keywords
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("IpHoNe", "11"));
        assertTrue(predicate.test(
                new PhoneBuilder().withName("iPhone 11").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        PhoneContainsKeywordsPredicate predicate =
                new PhoneContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PhoneBuilder().withCost("$1820").build()));

        // Non-matching keyword
        predicate = new PhoneContainsKeywordsPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PhoneBuilder().withCost("$1820").build()));

    }
}
