package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

public class ColumnTest {
    @Test
    public void getSize_skeleton_true() {
        assertEquals(0, new Column().getSize());
    }

    // Change this to return Interviewee
    @Test
    public void getInterviewer_skeleton_true() {
        Person person = new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@mail.com"),
            new Address("Singapore"), new HashSet<>());
        assertEquals(person, new Column().getInterviewer());
    }

    // Change this to return Interivewee instead later
    @Test
    public void getInterviewee_indexSkeleton_true() {
        Person person = new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@mail.com"),
            new Address("Singapore"), new HashSet<>());
        assertEquals(person, new Column().getInterviewee(0));
    }

    // Change this to return Interivewee instead later
    @Test
    public void getInterviewee_timingSkeleton_true() {
        Person person = new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@mail.com"),
            new Address("Singapore"), new HashSet<>());
        assertEquals(person, new Column().getInterviewee("26/10/2019 6:00pm-6:30pm"));
    }
}
