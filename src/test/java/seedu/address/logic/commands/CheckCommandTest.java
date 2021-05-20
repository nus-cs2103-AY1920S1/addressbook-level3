package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FinSec;
import seedu.address.model.ModelStub;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.claim.Claim;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;
import seedu.address.testutil.ClaimBuilder;
import seedu.address.testutil.ContactBuilder;
import seedu.address.ui.UiManager;

//@@author {lawncegoh}
class CheckCommandTest {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CheckCommand(null));
    }

    @Test
    public void execute_checkValidIndexInClaims_checkSuccess() throws CommandException {
        UiManager.changeState("claims");
        ModelStubForValidViewClaims model = new ModelStubForValidViewClaims();
        Index validIndex = Index.fromZeroBased(0);
        Claim claimToShow = model.getClaim();
        CommandResult commandResult = new CheckCommand(validIndex).execute(model);

        assertEquals("Claim Shown" , commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_checkValidIndexInContacts_checkSuccess() throws CommandException {
        UiManager.changeState("contacts");
        ModelStubForValidViewContacts model = new ModelStubForValidViewContacts();
        Index validIndex = Index.fromZeroBased(0);
        Contact contactToShow = model.getContact();
        CommandResult commandResult = new CheckCommand(validIndex).execute(model);

        assertEquals("Contact Shown", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_checkInvalidIndexInContacts_throwsCommandException() throws CommandException {
        UiManager.changeState("contacts");
        ModelStubForValidViewContacts model = new ModelStubForValidViewContacts();
        Index invalidIndex = Index.fromZeroBased(1);
        Contact contactToShow = model.getContact();

        assertThrows(CommandException.class, () -> new CheckCommand(invalidIndex).execute(model));
    }

    @Test
    public void execute_checkInvalidInIncomes_throwsCommandException() throws CommandException {
        UiManager.changeState("incomes");
        ModelStubForValidViewContacts model = new ModelStubForValidViewContacts();
        Index validIndex = Index.fromZeroBased(1);

        assertThrows(CommandException.class, () -> new CheckCommand(validIndex).execute(model));
    }

    @Test
    public void equals() {
        CheckCommand checkFirstCommand = new CheckCommand(INDEX_FIRST_PERSON);
        CheckCommand checkSecondCommand = new CheckCommand(INDEX_SECOND_PERSON);

        // same object -> true
        assertTrue(checkFirstCommand.equals(checkFirstCommand));

        // same values -> returns true
        CheckCommand checkFirstCommandCopy = new CheckCommand(INDEX_FIRST_PERSON);
        assertEquals(checkFirstCommand, checkFirstCommandCopy);

        // different types -> false
        assertFalse(checkFirstCommand.equals(1));

        // null -> false
        assertFalse(checkFirstCommand.equals(null));

        //different index -> false
        assertFalse(checkFirstCommand.equals(checkSecondCommand));
    }

    /**
     * Returns a modelstub for claims
     */
    private class ModelStubForValidViewClaims extends ModelStub {

        private final FinSecStub finSec = new FinSecStub();

        private Claim claim = new ClaimBuilder().build();

        ModelStubForValidViewClaims() {
            addClaim(claim);
        }

        public Claim getClaim() {
            return this.claim;
        }

        @Override
        public void addClaim(Claim claim) {
            finSec.addClaim(claim);
        }


        @Override
        public ObservableList<Claim> getFilteredClaimList() {
            return finSec.getClaimList();
        }
    }

    /**
     * Returns a modelstub for contacts
     */
    private class ModelStubForValidViewContacts extends ModelStub {

        private final FinSecStub finSec = new FinSecStub();

        private Contact contact = new ContactBuilder().build();

        ModelStubForValidViewContacts() {
            addContact(contact);
        }

        public Contact getContact() {
            return this.contact;
        }

        @Override
        public void addContact(Contact p) {
            finSec.addContact(contact);
        }

        @Override
        public ObservableList<Contact> getFilteredContactList() {
            return finSec.getContactList();
        }
    }

    /**
     * A stub ReadOnlyContact whose contacts list can violate interface constraints.
     */
    private static class FinSecStub extends FinSec {

        private final ObservableList<Contact> contacts = FXCollections.observableArrayList();
        private final ObservableList<Income> incomes = FXCollections.observableArrayList();
        private final ObservableList<Claim> claims = FXCollections.observableArrayList();
        private final ObservableList<AutocorrectSuggestion> suggestions = FXCollections.observableArrayList();
        private final ObservableList<CommandItem> commands = FXCollections.observableArrayList();

        FinSecStub() {
        }

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

        @Override
        public void setContacts(List<Contact> contacts) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClaims(List<Claim> claims) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIncomes(List<Income> incomes) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAutocorrectSuggestions(List<AutocorrectSuggestion> suggestions) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyFinSec newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasContact(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Contact findContactFor(Name name) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Contact findContactFor(Claim claim) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addContact(Contact p) {
            contacts.add(p);
        }

        @Override
        public void setContact(Contact target, Contact editedContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeContact(Contact key) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClaim(Claim claim) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClaim(Claim c) {
            claims.add(c);
        }

        @Override
        public void setClaim(Claim target, Claim editedClaim) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeClaim(Claim key) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void approveClaim(Claim target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void rejectClaim(Claim target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasIncome(Income income) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addIncome(Income income) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setIncome(Income target, Income editedIncome) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeIncome(Income income) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAutocorrectSuggestion(AutocorrectSuggestion target, AutocorrectSuggestion editedSuggestion) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCommand(CommandItem command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCommand(CommandItem e) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeCommand(CommandItem key) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCommands(List<CommandItem> commands) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCommand(CommandItem target, CommandItem editedCommand) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String toString() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean equals(Object other) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int hashCode() {
            throw new AssertionError("This method should not be called.");
        }
    }
}
