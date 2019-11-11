package seedu.jarvis.model.planner.predicates;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.jarvis.model.planner.tasks.Deadline;
import seedu.jarvis.model.planner.tasks.Event;
import seedu.jarvis.model.planner.tasks.Task;
import seedu.jarvis.model.planner.tasks.Todo;


class TaskDateMatchesDatePredicateTest {

    @Test
    void test_taskMatchesSingleDate() {
        TaskDateMatchesDatePredicate predicate = new TaskDateMatchesDatePredicate(false);
        LocalDate date = LocalDate.now();
        Deadline d;
        Event e;

        //deadline is on the date
        d = new Deadline("testing", date);
        assertTrue(predicate.test(d));

        //deadline is not on the date -> returns false
        LocalDate dd = LocalDate.parse("3/11/2016", Task.getDateFormat());
        d = new Deadline("test", dd);
        predicate = new TaskDateMatchesDatePredicate(false);
        assertFalse(predicate.test(d));

        //{@code Todo} -> returns false
        Todo t = new Todo("bb");
        assertFalse(predicate.test(t));

        //date falls in the middle of an event
        LocalDate start = date.minusDays(1);
        LocalDate end = date.plusDays(3);
        e = new Event("test", start, end);
        assertTrue(predicate.test(e));

        //date is on the start date of the event
        end = date.plusDays(4);
        e = new Event("testing", date, end);
        assertTrue(predicate.test(e));

        //date is on the end date of event
        start = date.minusDays(3);
        e = new Event("test", start, date);
        assertTrue(predicate.test(e));

        //date does not fall within event dates
        start = date.plusDays(1);
        end = date.plusDays(4);
        e = new Event("test", start, end);
        assertFalse(predicate.test(e));

    }

    @Test
    void test_taskMatchesDatesInWeek() {

        LocalDate date = LocalDate.now();
        TaskDateMatchesDatePredicate predicate = new TaskDateMatchesDatePredicate(true);
        Deadline d;
        Event e;

        //{@code Todo} -> returns false
        Todo t = new Todo("test");
        assertFalse(predicate.test(t));

        //deadline falls within the week
        LocalDate dd = date.plusDays(6);
        d = new Deadline("test", dd);
        assertTrue(predicate.test(d));

        //deadline is outside the week -> returns false
        dd = date.plusDays(7);
        d = new Deadline("test", dd);
        assertFalse(predicate.test(d));

        //event falls within the week
        LocalDate start = date.plusDays(1);
        LocalDate end = date.plusDays(3);
        e = new Event("t", start, end);
        assertTrue(predicate.test(e));

        //event starts within the week but ends beyond the week
        start = date.plusDays(2);
        end = date.plusDays(10);
        e = new Event("e", start, end);
        assertTrue(predicate.test(e));

        //event starts beyond the week -> returns false
        start = date.plusDays(10);
        end = date.plusDays(15);
        e = new Event("e", start, end);
        assertFalse(predicate.test(e));


    }

    @Test
    void testEquals() {

        TaskDateMatchesDatePredicate firstPredicate = new TaskDateMatchesDatePredicate(false);
        TaskDateMatchesDatePredicate secondPredicate = new TaskDateMatchesDatePredicate(
                                                            LocalDate.parse("10/10/2010", Task.getDateFormat()));

        //same object
        assertEquals(firstPredicate, firstPredicate);

        //same values
        TaskDateMatchesDatePredicate copy = new TaskDateMatchesDatePredicate(
                                                    LocalDate.parse("10/10/2010", Task.getDateFormat()));
        assertEquals(copy, secondPredicate);


        //different object types
        assertNotEquals(firstPredicate, 1);

        //null
        assertNotNull(firstPredicate);

        //different predicates of the same type
        assertNotEquals(firstPredicate, secondPredicate);
    }
}
