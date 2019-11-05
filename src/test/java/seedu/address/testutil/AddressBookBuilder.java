package seedu.address.testutil;

import seedu.address.model.StudyBuddyPro;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private StudyBuddyPro studyBuddyPro;

    public AddressBookBuilder() {
        studyBuddyPro = new StudyBuddyPro();
    }

    public AddressBookBuilder(StudyBuddyPro studyBuddyPro) {
        this.studyBuddyPro = studyBuddyPro;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withPerson(Person person) {
        studyBuddyPro.addPerson(person);
        return this;
    }

    public StudyBuddyPro build() {
        return studyBuddyPro;
    }
}
