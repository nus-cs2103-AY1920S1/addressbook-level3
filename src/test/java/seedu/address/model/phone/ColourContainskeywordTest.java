package seedu.address.model.phone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.phone.predicates.ColourContainsKeywordsPredicate;
import seedu.address.testutil.PhoneBuilder;

public class ColourContainskeywordTest {

    @Test
    void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("blue");
        List<String> secondPredicateKeywordList = Arrays.asList("blue", "red");

        ColourContainsKeywordsPredicate firstPredicate =
                new ColourContainsKeywordsPredicate(firstPredicateKeywordList);
        ColourContainsKeywordsPredicate secondPredicate =
                new ColourContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertEquals(firstPredicate, firstPredicate);

        // same values -> returns true
        ColourContainsKeywordsPredicate firstPredicateCopy =
                new ColourContainsKeywordsPredicate(firstPredicateKeywordList);
        assertEquals(firstPredicate, firstPredicateCopy);

        // different types -> returns false
        assertNotEquals(1, firstPredicate);

        // null -> returns false
        assertNotEquals(null, firstPredicate);

        // different phone -> returns false
        assertNotEquals(firstPredicate, secondPredicate);
    }


    @Test
    void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        ColourContainsKeywordsPredicate predicate =
                new ColourContainsKeywordsPredicate(Collections.singletonList("Blue"));
        assertTrue(predicate.test(new PhoneBuilder().withColour("Blue").build()));

        // Multiple keywords
        predicate = new ColourContainsKeywordsPredicate(Arrays.asList("grey"));
        assertTrue(predicate.test(new PhoneBuilder().withColour("grey").build()));

        // Mixed-case keywords
        predicate = new ColourContainsKeywordsPredicate(Arrays.asList("aLIce"));
        assertTrue(predicate.test(new PhoneBuilder().withColour("Alice").build()));
    }

    @Test
    void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ColourContainsKeywordsPredicate predicate = new ColourContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PhoneBuilder().withColour("dadaadadada").build()));

        // Non-matching keyword
        predicate = new ColourContainsKeywordsPredicate(Collections.singletonList("dadad"));
        assertFalse(predicate.test(new PhoneBuilder().withColour("y").build()));

        // Keywords match brand and name, but does not match colour
        predicate = new ColourContainsKeywordsPredicate(Arrays.asList("Alice", "iPhone", "Arrr"));
        assertFalse(predicate.test(new PhoneBuilder().withName("Alice")
                .withBrand("iPhone").withColour("Blue").build()));

        // Non-matching keyword
        predicate = new ColourContainsKeywordsPredicate(Collections.singletonList("dadad"));
        assertFalse(predicate.test(new PhoneBuilder().withColour("y").build()));
    }
}
