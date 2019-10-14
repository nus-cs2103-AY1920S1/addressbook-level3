package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Skeleton for RowTest class
 */
public class RowTest {
    @Test
    public void getSize_skeleton_true() {
        assertEquals(0, new Row().getSize());
    }

    @Test
    public void getTiming_skeleton_true() {
        assertNull(new Row().getTiming());
    }

    @Test
    public void getInterviewee_skeleton_true() {
        Person person = new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@mail.com"),
            new Address("Singapore"), new HashSet<>());
        assertEquals(person, new Row().getInterviewee(0));
    }
}
