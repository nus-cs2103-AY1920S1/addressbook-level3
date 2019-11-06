package seedu.jarvis.model.planner.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.jarvis.commons.core.tag.Tag;
import seedu.jarvis.model.planner.tasks.Todo;


class TaskTagMatchesTagPredicateTest {

    @Test
    void test() {
        Tag tag = new Tag("name");

        TaskTagMatchesTagPredicate predicate = new TaskTagMatchesTagPredicate(tag);
        Todo t;

        //task does not have Tag -> returns false
        t = new Todo("test");
        assertFalse(predicate.test(t));

        //task contains different Tag -> returns false
        t = new Todo("test");
        t.addTag(new Tag("school"));
        assertFalse(predicate.test(t));

        //task has same Tag
        t = new Todo("test");
        t.addTag(new Tag("name"));
        assertTrue(predicate.test(t));
    }


    @Test
    void testEquals() {
        TaskTagMatchesTagPredicate firstPredicate =
                new TaskTagMatchesTagPredicate(new Tag("name"));
        TaskTagMatchesTagPredicate secondPredicate =
                new TaskTagMatchesTagPredicate(new Tag("school"));

        //same object
        assertEquals(firstPredicate, firstPredicate);

        //same values
        TaskTagMatchesTagPredicate copy = new TaskTagMatchesTagPredicate(new Tag("name"));
        assertEquals(firstPredicate, copy);

        //different object types
        assertNotEquals(firstPredicate, 1);

        //null
        assertNotNull(firstPredicate);

        //different predicates of the same type
        assertNotEquals(firstPredicate, secondPredicate);

    }
}
