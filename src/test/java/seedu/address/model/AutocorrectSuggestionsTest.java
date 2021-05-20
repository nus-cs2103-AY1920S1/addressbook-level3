package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddContactCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.contact.Contact;
import seedu.address.testutil.ContactBuilder;

public class AutocorrectSuggestionsTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddContactCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessfulSuggestionTest() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Contact validPerson = new ContactBuilder().build();

        CommandResult commandResult = new AddContactCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddContactCommand.MESSAGE_SUCCESS, validPerson), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        assertTrue(modelStub.hasAutocorrectSuggestion(new AutocorrectSuggestion("add_claim n/"
                + validPerson.getName().fullName)));
    }



    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Contact contact;

        ModelStubWithPerson(Contact contact) {
            requireNonNull(contact);
            this.contact = contact;
        }

        @Override
        public boolean hasContact(Contact contact) {
            requireNonNull(contact);
            return this.contact.isSamePerson(contact);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Contact> personsAdded = new ArrayList<>();
        final ArrayList<AutocorrectSuggestion> suggestionsAdded = new ArrayList<>();

        @Override
        public boolean hasContact(Contact contact) {
            requireNonNull(contact);
            return personsAdded.stream().anyMatch(contact::isSamePerson);
        }

        @Override
        public boolean hasAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
            requireNonNull(suggestion);
            return suggestionsAdded.stream().anyMatch(suggestion::isSameAutoCorrectionSuggestion);
        }

        @Override
        public void addContact(Contact contact) {
            requireNonNull(contact);
            personsAdded.add(contact);
        }

        @Override
        public void addAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
            suggestionsAdded.add(suggestion);
        }

        @Override
        public ReadOnlyFinSec getFinSec() {
            return new FinSec();
        }
    }

}
