package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalObjects.ALICE;
import static seedu.address.testutil.TypicalObjects.AMY;
import static seedu.address.testutil.TypicalObjects.CLAIM_1;
import static seedu.address.testutil.TypicalObjects.COMMAND_ITEM_1;
import static seedu.address.testutil.TypicalObjects.INCOME_1;
import static seedu.address.testutil.TypicalObjects.SUGGESTION_1;
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
import seedu.address.testutil.ClaimBuilder;
import seedu.address.testutil.ContactBuilder;
import seedu.address.testutil.IncomeBuilder;

public class FinSecTest {

    private final FinSec finSec = new FinSec();

    @Test
    public void constructor() {

        assertEquals(Collections.emptyList(), finSec.getContactList());
        assertEquals(Collections.emptyList(), finSec.getIncomeList());
        assertEquals(Collections.emptyList(), finSec.getAutocorrectSuggestionList());
        assertEquals(Collections.emptyList(), finSec.getClaimList());
        assertEquals(Collections.emptyList(), finSec.getCommandsList());

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
        List<AutocorrectSuggestion> newSuggestions = Arrays.asList();
        List<CommandItem> newCommands = Arrays.asList();
        FinSecStub newData = new FinSecStub(newContacts, newIncomes, newClaims, newSuggestions, newCommands);

        assertThrows(DuplicateContactException.class, () -> finSec.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> finSec.hasContact((Name) null));
    }

    @Test
    public void hasIncome_nullIncome_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> finSec.hasIncome((Income) null));
    }

    @Test
    public void hasClaim_nullClaim_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> finSec.hasClaim((Claim) null));
    }

    @Test
    public void hasAutocorrectSuggestion_nullAutocorrectSuggestion_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> finSec.hasAutocorrectSuggestion((AutocorrectSuggestion) null));
    }

    @Test
    public void hasCommand_nullCommand_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> finSec.hasCommand((CommandItem) null));
    }

    @Test
    public void hasContact_contactNotInFinSec_returnsFalse() {
        assertFalse(finSec.hasContact(ALICE));
    }

    @Test
    public void hasClaim_claimNotInFinSec_returnsFalse() {
        assertFalse(finSec.hasClaim(CLAIM_1));
    }

    @Test
    public void hasIncome_incomeNotInFinSec_returnsFalse() {
        assertFalse(finSec.hasIncome(INCOME_1));
    }

    @Test
    public void hasAutocorrectSuggestion_suggestionNotInFinSec_returnsFalse() {
        assertFalse(finSec.hasAutocorrectSuggestion(SUGGESTION_1));
    }

    @Test
    public void hasCommandItem_commandNotInFinSec_returnsFalse() {
        assertFalse(finSec.hasCommand(COMMAND_ITEM_1));
    }

    @Test
    public void hasContact_contactInFinSec_returnsTrue() {
        finSec.addContact(ALICE);
        assertTrue(finSec.hasContact(ALICE));
    }

    @Test
    public void hasClaim_claimInFinSec_returnsTrue() {
        finSec.addContact(AMY);
        finSec.addClaim(CLAIM_1);
        assertTrue(finSec.hasClaim(CLAIM_1));
    }

    @Test
    public void hasIncome_incomeInFinSec_returnsTrue() {
        finSec.addIncome(INCOME_1);
        assertTrue(finSec.hasIncome(INCOME_1));
    }

    @Test
    public void hasAutocorrectSuggestion_suggestionInFinSec_returnsTrue() {
        finSec.addAutocorrectSuggestion(SUGGESTION_1);
        assertTrue(finSec.hasAutocorrectSuggestion(SUGGESTION_1));
    }

    @Test
    public void hasCommandItem_commandInFinSec_returnsTrue() {
        finSec.addCommand(COMMAND_ITEM_1);
        assertTrue(finSec.hasCommand(COMMAND_ITEM_1));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInFinSec_returnsTrue() {
        finSec.addContact(ALICE);
        Contact editedAlice = new ContactBuilder(ALICE)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(finSec.hasContact(editedAlice));
    }

    @Test
    public void hasClaim_claimWithSameIdentityFieldsInFinSec_returnsTrue() {
        finSec.addContact(AMY);
        finSec.addClaim(CLAIM_1);
        Claim editedClaim = new ClaimBuilder(CLAIM_1)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(finSec.hasClaim(editedClaim));
    }

    @Test
    public void hasIncome_incomeWithSameIdentityFieldsInFinSec_returnsTrue() {
        finSec.addIncome(INCOME_1);
        Income editedIncome = new IncomeBuilder(INCOME_1)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(finSec.hasIncome(editedIncome));
    }


    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> finSec.getContactList().remove(0));
    }

    @Test
    public void getClaimList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> finSec.getClaimList().remove(0));
    }

    @Test
    public void getIncomeList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> finSec.getIncomeList().remove(0));
    }

    @Test
    public void getAutosuggestionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> finSec.getAutocorrectSuggestionList().remove(0));
    }

    @Test
    public void getCommandList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> finSec.getCommandsList().remove(0));
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

        FinSecStub(Collection<Contact> contacts, Collection<Income> incomes, Collection<Claim> claims,
                   Collection<AutocorrectSuggestion> autocorrectSuggestions, Collection<CommandItem> commands) {
            this.contacts.setAll(contacts);
            this.incomes.setAll(incomes);
            this.claims.setAll(claims);
            this.suggestions.setAll(autocorrectSuggestions);
            this.commands.setAll(commands);

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
