package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalObjects.ALICE;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.claim.Claim;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.exceptions.DuplicateContactException;
import seedu.address.model.income.Income;
import seedu.address.testutil.ContactBuilder;

public class FinSecTest {

    private final FinSec finSec = new FinSec();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), finSec.getContactList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> finSec.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        FinSec newData = getTypicalFinSec();
        finSec.resetData(newData);
        assertEquals(newData, finSec);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two contacts with the same identity fields
        seedu.address.model.contact.Contact editedAlice = new ContactBuilder(ALICE)
                .withTags(VALID_TAG_HUSBAND).build();
        List<seedu.address.model.contact.Contact> newContacts = Arrays.asList(ALICE, editedAlice);
        List<Income> newIncomes = Arrays.asList();
        List<Claim> newClaims = Arrays.asList();
        FinSecStub newData = new FinSecStub(newContacts, newIncomes, newClaims);

        assertThrows(DuplicateContactException.class, () -> finSec.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> finSec.hasContact(new Name(null)));
    }

    @Test
    public void hasContact_contactNotInAddressBook_returnsFalse() {
        assertFalse(finSec.hasContact(ALICE));
    }

    @Test
    public void hasContact_contactInAddressBook_returnsTrue() {
        finSec.addContact(ALICE);
        assertTrue(finSec.hasContact(ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        finSec.addContact(ALICE);
        seedu.address.model.contact.Contact editedAlice = new ContactBuilder(ALICE)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(finSec.hasContact(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> finSec.getContactList().remove(0));
    }

    /**
     * A stub ReadOnlyContact whose contacts list can violate interface constraints.
     */
    private static class FinSecStub implements ReadOnlyFinSec {

        private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
        private final ObservableList<Income> incomes = FXCollections.observableArrayList();
        private final ObservableList<Claim> claims = FXCollections.observableArrayList();
        private final ObservableList<AutocorrectSuggestion> suggestions = FXCollections.observableArrayList();
        private final ObservableList<CommandItem> commands = FXCollections.observableArrayList();

        FinSecStub(Collection<Contact> contacts, Collection<Income> incomes, Collection<Claim> claims) {
            this.contacts.setAll(contacts);
            this.incomes.setAll(incomes);
            this.claims.setAll(claims);
        }

        @Override
        public ObservableList<seedu.address.model.contact.Contact> getContactList() {
            return contacts;
        }

        @Override
        public ObservableList<Income> getIncomeList() {
            return incomes;
        }

        @Override
        public ObservableList<Claim> getClaimList() {
            return claims;
        }

        @Override
        public ObservableList<AutocorrectSuggestion> getAutocorrectSuggestionList() {
            return suggestions;
        }

        @Override
        public ObservableList<CommandItem> getCommandsList() {
            return commands;
        }
    }

}
