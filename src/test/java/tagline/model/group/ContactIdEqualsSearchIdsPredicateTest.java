//@@author e0031374
package tagline.model.group;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import tagline.model.contact.ContactBuilder;

public class ContactIdEqualsSearchIdsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("00001");
        List<String> firstAlterPredicateKeywordList = Collections.singletonList("1");
        List<String> secondPredicateKeywordList = Arrays.asList("00001", "00002");

        ContactIdEqualsSearchIdsPredicate firstPredicate =
                new ContactIdEqualsSearchIdsPredicate(firstPredicateKeywordList);
        ContactIdEqualsSearchIdsPredicate firstAlterPredicate =
                new ContactIdEqualsSearchIdsPredicate(firstAlterPredicateKeywordList);
        ContactIdEqualsSearchIdsPredicate secondPredicate =
                new ContactIdEqualsSearchIdsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ContactIdEqualsSearchIdsPredicate firstPredicateCopy =
                new ContactIdEqualsSearchIdsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));
        assertTrue(firstPredicate.equals(firstAlterPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different contact -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
        assertFalse(firstAlterPredicate.equals(secondPredicate));
    }

    @Test
    public void test_contactIdEqualsKeywords_returnsTrue() {
        // One keyword

        ContactIdEqualsSearchIdsPredicate predicate =
                new ContactIdEqualsSearchIdsPredicate(Collections.singletonList("00031"));
        assertTrue(predicate.test(new ContactBuilder().withName("Jimin").withId(31).build()));
        assertFalse(predicate.test(new ContactBuilder().withName("Jungkook").withId(310).build()));

        // Multiple keywords
        predicate = new ContactIdEqualsSearchIdsPredicate(Arrays.asList("00031", "310"));
        assertTrue(predicate.test(new ContactBuilder().withName("Jimin").withId(31).build()));
        assertTrue(predicate.test(new ContactBuilder().withName("Jungkook").withId(310).build()));

        // Only one matching keyword
        predicate = new ContactIdEqualsSearchIdsPredicate(Arrays.asList("00031", "912"));
        assertTrue(predicate.test(new ContactBuilder().withName("Jimin").withId(31).build()));
        assertFalse(predicate.test(new ContactBuilder().withName("Jungkook").withId(310).build()));
    }

    @Test
    public void test_contactDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        ContactIdEqualsSearchIdsPredicate predicate = new ContactIdEqualsSearchIdsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new ContactBuilder().withName("Jimin").withId(31).build()));
        assertFalse(predicate.test(new ContactBuilder().withName("Jungkook").withId(310).build()));

        // Non-matching keyword
        predicate = new ContactIdEqualsSearchIdsPredicate(Arrays.asList("00311"));
        assertFalse(predicate.test(new ContactBuilder().withName("Jimin").withId(31).build()));
        assertFalse(predicate.test(new ContactBuilder().withName("Jungkook").withId(310).build()));

        // Multiple Non-matching keyword
        predicate = new ContactIdEqualsSearchIdsPredicate(Arrays.asList("00311", "312"));
        assertFalse(predicate.test(new ContactBuilder().withName("Jimin").withId(31).build()));
        assertFalse(predicate.test(new ContactBuilder().withName("Jungkook").withId(310).build()));
    }
}
