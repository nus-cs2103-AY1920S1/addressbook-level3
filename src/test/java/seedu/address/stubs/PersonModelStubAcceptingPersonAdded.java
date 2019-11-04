package seedu.address.stubs;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import seedu.address.person.model.AddressBook;
import seedu.address.person.model.ReadOnlyAddressBook;
import seedu.address.person.model.person.Person;

/**
 * Represents an address book Model stub.
 */
public class PersonModelStubAcceptingPersonAdded extends PersonModelStub {
    final ArrayList<Person> personsAdded;

    public PersonModelStubAcceptingPersonAdded() {
        personsAdded = new ArrayList<>();
    }


    public PersonModelStubAcceptingPersonAdded(ArrayList<Person> arr) {
        this.personsAdded = arr;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return personsAdded.stream().anyMatch(person::isSamePerson);
    }

    @Override
    public void addPerson(Person person) {
        requireNonNull(person);
        personsAdded.add(person);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return new AddressBook();
    }

    public ArrayList<Person> getPersonAdded() {
        return personsAdded;
    }
}
