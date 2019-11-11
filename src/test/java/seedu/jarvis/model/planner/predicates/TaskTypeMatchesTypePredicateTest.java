package seedu.jarvis.model.planner.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.planner.enums.TaskType;
import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;

class TaskTypeMatchesTypePredicateTest {

    @Test
    void test() {
        TaskTypeMatchesTypePredicate predicate = new TaskTypeMatchesTypePredicate(TaskType.TODO);
        Task d;

        //task contains different TaskType level -> returns false
        d = new Deadline("test", LocalDate.now());
        assertFalse(predicate.test(d));

        //task has same TaskType level
        d = new Todo("test");
        assertTrue(predicate.test(d));
    }


    @Test
    void testEquals() {
        TaskTypeMatchesTypePredicate firstPredicate =
                new TaskTypeMatchesTypePredicate(TaskType.DEADLINE);
        TaskTypeMatchesTypePredicate secondPredicate =
                new TaskTypeMatchesTypePredicate(TaskType.TODO);

        //same object
        assertEquals(firstPredicate, firstPredicate);

        //same values
        TaskTypeMatchesTypePredicate copy = new TaskTypeMatchesTypePredicate(TaskType.DEADLINE);
        assertEquals(firstPredicate, copy);

        //different object types
        assertNotEquals(firstPredicate, 1);

        //null
        assertNotNull(firstPredicate);

        //different predicates of the same type
        assertNotEquals(firstPredicate, secondPredicate);

    }
}
