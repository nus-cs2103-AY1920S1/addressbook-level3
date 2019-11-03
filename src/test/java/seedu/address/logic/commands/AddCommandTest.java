package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Context;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Person;
import seedu.address.stub.ModelStub;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubAcceptingPersonAdded();
        modelStub.addPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_NAME, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_duplicateName_throwsCommandException() {
        Person invalidPerson = new PersonBuilder().withName("George Best").build();
        AddCommand addCommand = new AddCommand(invalidPerson);
        ModelStub modelStub = new ModelStubAcceptingPersonAdded();
        modelStub.addPerson(TypicalPersons.GEORGE);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_NAME, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A Model stub that always accept a context and the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        private Context context;

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

        @Override
        public Optional<Person> findPersonByName(String searchTerm) {
            requireNonNull(searchTerm);
            for (Person person : personsAdded) {
                if (person.getName().fullName.toLowerCase().equals(searchTerm.toLowerCase())) {
                    return Optional.of(person);
                }
            }
            return Optional.empty();
        }

        @Override
        public void setContext(Context context) {
            this.context = context;
        }
    }

}
