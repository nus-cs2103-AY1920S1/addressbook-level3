package seedu.jarvis.model.planner.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.planner.enums.Priority;
import seedu.jarvis.model.planner.tasks.Todo;


class TaskPriorityMatchesPriorityPredicateTest {

    @Test
    void test() {
        TaskPriorityMatchesPriorityPredicate predicate = new TaskPriorityMatchesPriorityPredicate(Priority.HIGH);
        Todo t;

        //task does not have Priority level -> returns false
        t = new Todo("test");
        assertFalse(predicate.test(t));

        //task contains different Priority level -> returns false
        t = new Todo("test");
        t.setPriority(Priority.LOW);
        assertFalse(predicate.test(t));

        //task has same Priority level
        t = new Todo("test");
        t.setPriority(Priority.HIGH);
        assertTrue(predicate.test(t));
    }


    @Test
    void testEquals() {
        TaskPriorityMatchesPriorityPredicate firstPredicate =
                new TaskPriorityMatchesPriorityPredicate(Priority.HIGH);
        TaskPriorityMatchesPriorityPredicate secondPredicate =
                new TaskPriorityMatchesPriorityPredicate(Priority.LOW);

        //same object
        assertEquals(firstPredicate, firstPredicate);

        //same values
        TaskPriorityMatchesPriorityPredicate copy = new TaskPriorityMatchesPriorityPredicate(Priority.HIGH);
        assertEquals(firstPredicate, copy);

        //different object types
        assertNotEquals(firstPredicate, 1);

        //null
        assertNotNull(firstPredicate);

        //different predicates of the same type
        assertNotEquals(firstPredicate, secondPredicate);

    }
}
