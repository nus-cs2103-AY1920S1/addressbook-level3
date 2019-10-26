package seedu.address.model.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import seedu.address.model.phone.predicates.BrandContainsKeywordsPredicate;
import seedu.address.testutil.PhoneBuilder;

public class BrandContainsKeywordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("one");
        List<String> secondPredicateKeywordList = Arrays.asList("one", "two");

        BrandContainsKeywordsPredicate  firstPred =
                new BrandContainsKeywordsPredicate(firstPredicateKeywordList);
        BrandContainsKeywordsPredicate  firstPredCopy =
                new BrandContainsKeywordsPredicate(firstPredicateKeywordList);
        BrandContainsKeywordsPredicate  secondPred =
                new BrandContainsKeywordsPredicate(secondPredicateKeywordList);

        //same obj
        assertTrue(firstPred.equals(firstPredCopy));

        //same value
        assertEquals(firstPred, firstPred);

        // different types -> returns false
        assertNotEquals(1, firstPred);

        // null -> returns false
        assertNotEquals(null, firstPred);

        // different phone -> returns false
        assertNotEquals(firstPred, secondPred);
    }

    @Test
    void test_brandContainsKeywords_returnsTrue() {
        // One keyword
        BrandContainsKeywordsPredicate predicate =
                new BrandContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PhoneBuilder().withBrand("Alice Bob").build()));

        // Multiple keywords
        predicate = new BrandContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PhoneBuilder().withBrand("Alice Bob").build()));

        // Only one matching keyword
        predicate = new BrandContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PhoneBuilder().withBrand("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new BrandContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PhoneBuilder().withBrand("Alice Bob").build()));
    }
}

