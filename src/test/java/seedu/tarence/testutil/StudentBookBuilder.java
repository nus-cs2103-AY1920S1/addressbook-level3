package seedu.tarence.testutil;

import seedu.tarence.model.StudentBook;
import seedu.tarence.model.person.Person;

/**
 * A utility class to help with building StudentBook objects.
 * Example usage: <br>
 *     {@code StudentBook ab = new StudentBookBuilder().withPerson("John", "Doe").build();}
 */
public class StudentBookBuilder {

    private StudentBook studentBook;

    public StudentBookBuilder() {
        studentBook = new StudentBook();
    }

    public StudentBookBuilder(StudentBook studentBook) {
        this.studentBook = studentBook;
    }

    /**
     * Adds a new {@code Person} to the {@code StudentBook} that we are building.
     */
    public StudentBookBuilder withPerson(Person person) {
        studentBook.addPerson(person);
        return this;
    }

    public StudentBook build() {
        return studentBook;
    }
}
