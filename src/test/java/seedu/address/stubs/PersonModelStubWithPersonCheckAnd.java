package seedu.address.stubs;

import static java.util.Objects.requireNonNull;

import seedu.address.person.model.person.Person;

/**
 * Represents an address book Model stub.
 */
public class PersonModelStubWithPersonCheckAnd extends PersonModelStubCheckAnd {
    protected final Person person;

    public PersonModelStubWithPersonCheckAnd(Person person) {
        requireNonNull(person);
        this.person = person;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return this.person.isSamePerson(person);
    }

}
