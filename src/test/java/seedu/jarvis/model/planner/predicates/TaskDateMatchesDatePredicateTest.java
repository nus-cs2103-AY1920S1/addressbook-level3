package seedu.jarvis.model.planner.predicates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskDateMatchesDatePredicateTest {

    @Test
    void test_taskMatchesSingleDate() {
        //deadline is on the date

        //deadline is not on the date -> returns false

        //todo -> returns false

        //date falls in the middle of an event

        //date is on the start date of the event

        //date is on the end date of event

    }

    @Test
    void test_taskMatchesDatesInWeek() {
        //todo -> returns false

        //deadline falls within the week

        //deadline is outside the week -> returns false

        //event falls within the week

        //event starts within the week but ends beyond the week

        //event starts beyond the week



    }

    @Test
    void testEquals() {

        //same object

        //same values

        //different object types

        //null

        //different predicates of the same type
    }
}