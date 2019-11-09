package com.typee.model.engagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.typee.model.person.Name;
import com.typee.model.person.Person;

public class AttendeeListTest {

    @Test
    public void isValid_validNames_returnTrue() {
        //Single entry
        assertTrue(AttendeeList.isValid(" Tom "));
        //Multiple entry
        assertTrue(AttendeeList.isValid(" Tom|Jerry|Mary|Sheep "));
    }

    @Test
    public void isValid_blankString_returnFalse() {
        assertFalse(AttendeeList.isValid("  "));
    }

    @Test
    public void isValid_invalidNames_returnFalse() {
        //Non-english characters
        assertFalse(AttendeeList.isValid("Früser Begrußman"));
        assertFalse(AttendeeList.isValid("宮崎かおる"));
    }

    @Test
    public void getListGivenValidInput() {
        //Single entry
        assertEquals(AttendeeList.getListGivenValidInput(" Tom ").getAttendees().get(0), new Person(new Name("Tom")));
        //Multiple entry
        assertEquals(AttendeeList.getListGivenValidInput(" Tom|Jerry|Mary|Sheep "),
                new AttendeeList(Arrays.asList(new Person(new Name("Tom")), new Person(new Name("Jerry")),
                        new Person(new Name("Mary")), new Person(new Name("Sheep")))));
    }

    @Test
    public void getAttendees() {
        assertEquals(AttendeeList.getListGivenValidInput(" Tom|Jerry|Mary|Sheep ").getAttendees(),
                Arrays.asList(new Person(new Name("Tom")), new Person(new Name("Jerry")),
                        new Person(new Name("Mary")), new Person(new Name("Sheep"))));
    }

    @Test
    public void toStringTest() {
        assertEquals(AttendeeList.getListGivenValidInput(" Tom|Jerry|Mary|Sheep ").toString(),
                "[Tom | Jerry | Mary | Sheep]");
    }

    @Test
    public void equalsTest() {
        AttendeeList attendeeList = new AttendeeList(Arrays.asList());
        //short-circuit equals
        assertTrue(attendeeList.equals(attendeeList));
        //not instanceof
        assertFalse(attendeeList.equals(Arrays.asList()));
    }
}
