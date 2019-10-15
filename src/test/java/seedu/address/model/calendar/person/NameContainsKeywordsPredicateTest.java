//package seedu.address.calendarModel.calendar.task;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.address.calendarModel.task.TaskTitleContainsKeywordsPredicate;
//import seedu.address.testutil.PersonBuilder;
//
//
//
//
//public class NameContainsKeywordsPredicateTest {
//
//    @Test
//    public void equals() {
//        List<String> firstPredicateKeywordList = Collections.singletonList("first");
//        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");
//
//        TaskTitleContainsKeywordsPredicate firstPredicate
//                = new TaskTitleContainsKeywordsPredicate(firstPredicateKeywordList);
//        TaskTitleContainsKeywordsPredicate secondPredicate
//                = new TaskTitleContainsKeywordsPredicate(secondPredicateKeywordList);
//
//        // same object -> returns true
//        assertTrue(firstPredicate.equals(firstPredicate));
//
//        // same values -> returns true
//        TaskTitleContainsKeywordsPredicate firstPredicateCopy
//                = new TaskTitleContainsKeywordsPredicate(firstPredicateKeywordList);
//        assertTrue(firstPredicate.equals(firstPredicateCopy));
//
//        // different types -> returns false
//        assertFalse(firstPredicate.equals(1));
//
//        // null -> returns false
//        assertFalse(firstPredicate.equals(null));
//
//        // different task -> returns false
//        assertFalse(firstPredicate.equals(secondPredicate));
//    }
//
//    @Test
//    public void test_nameContainsKeywords_returnsTrue() {
//        // One keyword
//        TaskTitleContainsKeywordsPredicate predicate
//                = new TaskTitleContainsKeywordsPredicate(Collections.singletonList("Alice"));
//        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
//
//        // Multiple keywords
//        predicate = new TaskTitleContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
//        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
//
//        // Only one matching keyword
//        predicate = new TaskTitleContainsKeywordsPredicate(Arrays.asList("Bob", "Carol"));
//        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));
//
//        // Mixed-case keywords
//        predicate = new TaskTitleContainsKeywordsPredicate(Arrays.asList("aLIce", "bOB"));
//        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
//    }
//
//    @Test
//    public void test_nameDoesNotContainKeywords_returnsFalse() {
//        // Zero keywords
//        TaskTitleContainsKeywordsPredicate predicate
//                = new TaskTitleContainsKeywordsPredicate(Collections.emptyList());
//        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));
//
//        // Non-matching keyword
//        predicate = new TaskTitleContainsKeywordsPredicate(Arrays.asList("Carol"));
//        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));
//
//        // Keywords match phone, email and address, but does not match name
//        predicate = new TaskTitleContainsKeywordsPredicate(
//                Arrays.asList("12345", "alice@email.com", "Main", "Street"));
//        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
//                .withEmail("alice@email.com").withAddress("Main Street").build()));
//    }
//}
