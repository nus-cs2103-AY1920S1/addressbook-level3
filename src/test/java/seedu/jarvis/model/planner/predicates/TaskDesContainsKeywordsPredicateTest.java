package seedu.jarvis.model.planner.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;




class TaskDesContainsKeywordsPredicateTest {

    @Test
    void test_nameContainsKeywords_returnsTrue() {
        //One keyword
        TaskDesContainsKeywordsPredicate predicate = new TaskDesContainsKeywordsPredicate(
                                                            Collections.singletonList("read"));
        Task t = new Todo("read book");
        assertTrue(predicate.test(t));

        //Multiple keywords
        predicate = new TaskDesContainsKeywordsPredicate(Arrays.asList("read", "book"));
        t = new Todo("read book");
        assertTrue(predicate.test(t));

        //Only one matching keyword
        predicate = new TaskDesContainsKeywordsPredicate(Arrays.asList("borrow", "book"));
        t = new Todo("read book");
        assertTrue(predicate.test(t));

        //Mixed-case keywords
        predicate = new TaskDesContainsKeywordsPredicate(Arrays.asList("reAd", "Book"));
        t = new Todo("reading book");
        assertTrue(predicate.test(t));

    }

    @Test
    void testEquals() {
        List<String> first = Collections.singletonList("first");
        List<String> second = Arrays.asList("first", "second");

        TaskDesContainsKeywordsPredicate firstPredicate = new TaskDesContainsKeywordsPredicate(first);
        TaskDesContainsKeywordsPredicate secondPredicate = new TaskDesContainsKeywordsPredicate(second);

        //same object
        assertEquals(firstPredicate, firstPredicate);

        //same values
        TaskDesContainsKeywordsPredicate copy = new TaskDesContainsKeywordsPredicate(first);
        assertEquals(firstPredicate, copy);

        //different types
        assertNotEquals(firstPredicate, 1);

        //null
        assertNotNull(firstPredicate);

        //different predicates of the same type
        assertNotEquals(firstPredicate, secondPredicate);
    }
}
