package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.EventBuilder;

class EventNameContainsKeywordsPredicateTest {

    @Test
    void testEventNameContainsKeywords() {
        // One keyword
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("Musical"));
        assertTrue(predicate.test(new EventBuilder().withName("Awesome Musical").build()));

        // Multiple keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Musical", "Pop"));
        assertTrue(predicate.test(new EventBuilder().withName("Pop Musical Concert").build()));

        // Only one matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Party", "Birthday"));
        assertTrue(predicate.test(new EventBuilder().withName("Birthday Outing").build()));

        // Mixed-case keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("pArtY", "birTHDay"));
        assertTrue(predicate.test(new EventBuilder().withName("Birthday Outing").build()));
    }

    @Test
    void testEventNameDoesNotContainKeywords() {
        // Zero keywords
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withName("Party").build()));

        // Non-matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("Party"));
        assertFalse(predicate.test(new EventBuilder().withName("Concert").build()));

        // Keywords match venue and manpower needed, but does not match name
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("5", "Musical", "Jurong"));
        assertFalse(predicate.test(new EventBuilder().withName("Rock Concert")
                .withManpowerNeeded("5").withVenue("Jurong").build()));
    }

    @Test
    void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EventNameContainsKeywordsPredicate firstPredicate =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);
        EventNameContainsKeywordsPredicate secondPredicate =
                new EventNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventNameContainsKeywordsPredicate firstPredicateCopy =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different employee -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }
}
