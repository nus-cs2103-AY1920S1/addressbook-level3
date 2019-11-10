package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.TimeBook;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

class SampleTimeBookTest {

    @Test
    void generateSampleTimeBook() {
        Person.counterReset();
        Group.counterReset();
        TimeBook timeBook1 = SampleTimeBook.generateSampleTimeBook();

        Person.counterReset();
        Group.counterReset();
        TimeBook timeBook2 = SampleTimeBook.generateSampleTimeBook();

        assertTrue(timeBook1.equals(timeBook2));
    }
}
