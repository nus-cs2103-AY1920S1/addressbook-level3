package seedu.address.model;

import java.util.HashSet;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Encapsulates a column in the schedule timetable.
 */
public class Column {

    public int getSize() {
        return 0;
    }

    // Change this to return Interviewee
    public Person getInterviewer() {
        return new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@mail.com"),
            new Address("Singapore"), new HashSet<>());
    }

    // Change this to return Interivewee instead later
    public Person getInterviewee(int index) {
        return new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@mail.com"),
            new Address("Singapore"), new HashSet<>());
    }

    // Change this to return Interivewee instead later
    public Person getInterviewee(String timing) {
        return new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@mail.com"),
            new Address("Singapore"), new HashSet<>());
    }
}
