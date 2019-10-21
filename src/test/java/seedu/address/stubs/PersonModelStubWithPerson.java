package seedu.address.stubs;

import static java.util.Objects.requireNonNull;

import seedu.address.person.model.person.Person;

/**
 * Represents an address book Model stub.
 */
public class PersonModelStubWithPerson extends PersonModelStub {
    protected final Person person;

    public PersonModelStubWithPerson(Person person) {
        requireNonNull(person);
        this.person = person;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return this.person.isSamePerson(person);
    }

}
