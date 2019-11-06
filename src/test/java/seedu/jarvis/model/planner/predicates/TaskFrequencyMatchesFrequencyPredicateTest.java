package seedu.jarvis.model.planner.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.planner.enums.Frequency;
import seedu.jarvis.model.planner.tasks.Todo;


class TaskFrequencyMatchesFrequencyPredicateTest {

    @Test
    void test() {
        TaskFrequencyMatchesFrequencyPredicate predicate =
                            new TaskFrequencyMatchesFrequencyPredicate(Frequency.MONTHLY);
        Todo t;

        //task does not have frequency level -> returns false
        t = new Todo("test");
        assertFalse(predicate.test(t));

        //task contains different frequency level -> returns false
        t = new Todo("test");
        t.setFrequency(Frequency.DAILY);
        assertFalse(predicate.test(t));

        //task has same frequency level
        t = new Todo("test");
        t.setFrequency(Frequency.MONTHLY);
        assertTrue(predicate.test(t));
    }


    @Test
    void testEquals() {
        TaskFrequencyMatchesFrequencyPredicate firstPredicate =
                new TaskFrequencyMatchesFrequencyPredicate(Frequency.MONTHLY);
        TaskFrequencyMatchesFrequencyPredicate secondPredicate =
                new TaskFrequencyMatchesFrequencyPredicate(Frequency.WEEKLY);

        //same object
        assertEquals(firstPredicate, firstPredicate);

        //same values
        TaskFrequencyMatchesFrequencyPredicate copy = new TaskFrequencyMatchesFrequencyPredicate(Frequency.MONTHLY);
        assertEquals(firstPredicate, copy);

        //different object types
        assertNotEquals(firstPredicate, 1);

        //null
        assertNotNull(firstPredicate);

        //different predicates of the same type
        assertNotEquals(firstPredicate, secondPredicate);

    }
}
