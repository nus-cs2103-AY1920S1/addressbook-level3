package seedu.address.model;

import java.util.HashSet;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;

/**
 * Encapsulates a row in the schedule timetable.
 */
public class Row {
    private String timing;

    public int getSize() {
        return 0;
    }

    public String getTiming() {
        return timing;
    }

    // Change this to return Interivewee instead later
    public Person getInterviewee(int index) {
        return new Person(new Name("John Doe"), new Phone("12345678"), new Email("johndoe@mail.com"),
            new Address("Singapore"), new HashSet<>());
    }
}
