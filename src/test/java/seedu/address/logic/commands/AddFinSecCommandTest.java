package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FinSec;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.claim.Claim;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;
import seedu.address.testutil.ContactBuilder;

public class AddFinSecCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddContactCommand(null));
    }

    /*
    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Contact validContact = new ContactBuilder().build();

        CommandResult commandResult = new AddContactCommand(validContact).execute(modelStub);

        AutocorrectSuggestion addName = new AutocorrectSuggestion(validContact.getName().toString());
        modelStub.addAutocorrectSuggestion(addName);
        SuggestionsStorage.setSuggestionList(modelStub.getFilteredAutocorrectSuggestionList());

        assertEquals(String.format(AddContactCommand.MESSAGE_SUCCESS, validContact),
                                                                          commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validContact), modelStub.personsAdded);
    }
    */

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Contact validContact = new ContactBuilder().build();
        AddContactCommand addContactCommand = new AddContactCommand(validContact);
        ModelStub modelStub = new ModelStubWithPerson(validContact);

        assertThrows(CommandException.class,
                AddContactCommand.MESSAGE_DUPLICATE_PERSON, () -> addContactCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Contact alice = new ContactBuilder().withName("Alice").build();
        Contact bob = new ContactBuilder().withName("Bob").build();
        AddContactCommand addAliceCommand = new AddContactCommand(alice);
        AddContactCommand addBobCommand = new AddContactCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddContactCommand addAliceCommandCopy = new AddContactCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different contact -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private abstract class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getFinSecFilePath() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinSecFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addContact(Contact contact) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinSec(ReadOnlyFinSec newData) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyFinSec getFinSec() {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasContact(Contact contact) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteContact(Contact target) {

            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContact(Contact target, Contact editedContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Contact> getFilteredContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClaim(Claim claim) {
            /*
            FUNCTION TO BE EDITED
             */
            return false;
        }

        @Override
        public void deleteClaim(Claim target) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public void addClaim(Claim claim) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public void setClaim(Claim target, Claim editedClaim) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public void approveClaim(Claim claim) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public void rejectClaim(Claim claim) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public boolean hasContactFor(Claim target) {
            /*
            FUNCTION TO BE EDITED
             */
            return false;
        }

        @Override
        public boolean hasIncome(Income income) {
            /*
            FUNCTION TO BE EDITED
             */
            return false;
        }

        @Override
        public void deleteIncome(Income target) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public void addIncome(Income income) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public void setIncome(Income target, Income editedIncome) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public boolean hasAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
            /*
            FUNCTION TO BE EDITED
             */
            return false;
        }

        @Override
        public void deleteAutocorrectSuggestion(AutocorrectSuggestion target) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public void addAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public void setAutocorrectSuggestion(AutocorrectSuggestion target, AutocorrectSuggestion editedSuggestion) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public void addCommand(CommandItem command) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public boolean hasCommand(CommandItem command) {
            return false;
        }

        @Override
        public void deleteCommand(CommandItem command) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public void setCommand(CommandItem command, CommandItem editedCommand) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public void saveCommand(String command) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public String getSavedCommand() {
            return "test";
        }

        @Override
        public ObservableList<Income> getFilteredIncomeList() {
            return null;
        }

        @Override
        public void updateFilteredIncomeList(Predicate<Income> predicate) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public ObservableList<Claim> getFilteredClaimList() {
            return null;
        }

        @Override
        public void updateFilteredClaimList(Predicate<Claim> predicate) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public ObservableList<AutocorrectSuggestion> getFilteredAutocorrectSuggestionList() {
            return null;
        }

        @Override
        public void updateFilteredAutocorrectSuggestionList(Predicate<AutocorrectSuggestion> predicate) {
            /*
            FUNCTION TO BE EDITED
             */
        }

        @Override
        public ObservableList<CommandItem> getFilteredCommandsList() {
            return null;
        }

        @Override
        public void updateFilteredCommandsList(Predicate<CommandItem> predicate) {
             /*
            FUNCTION TO BE EDITED
             */
        }


    }

    /**
     * A Model stub that contains a single contact.
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

        @Override
        public void sortFilteredContactListByName() {

        }

        @Override
        public void sortReverseFilteredContactListByName() {

        }

        @Override
        public void sortFilteredClaimListByName() {

        }

        @Override
        public void sortFilteredClaimListByDate() {

        }

        @Override
        public void sortFilteredClaimListByStatus() {

        }

        @Override
        public void sortReverseFilteredClaimListByName() {

        }

        @Override
        public void sortReverseFilteredClaimListByDate() {

        }

        @Override
        public void sortReverseFilteredClaimListByStatus() {

        }

        @Override
        public void sortFilteredIncomeListByName() {

        }

        @Override
        public void sortFilteredIncomeListByDate() {

        }

        @Override
        public void sortReverseFilteredIncomeListByName() {

        }

        @Override
        public void sortReverseFilteredIncomeListByDate() {

        }
    }

    /**
     * A Model stub that always accept the contact being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Contact> personsAdded = new ArrayList<>();

        @Override
        public boolean hasContact(Contact contact) {
            requireNonNull(contact);
            return personsAdded.stream().anyMatch(contact::isSamePerson);
        }

        @Override
        public void addContact(Contact contact) {
            requireNonNull(contact);
            personsAdded.add(contact);
        }

        @Override
        public void sortFilteredContactListByName() {

        }

        @Override
        public void sortReverseFilteredContactListByName() {

        }

        @Override
        public void sortFilteredClaimListByName() {

        }

        @Override
        public void sortFilteredClaimListByDate() {

        }

        @Override
        public void sortFilteredClaimListByStatus() {

        }

        @Override
        public void sortReverseFilteredClaimListByName() {

        }

        @Override
        public void sortReverseFilteredClaimListByDate() {

        }

        @Override
        public void sortReverseFilteredClaimListByStatus() {

        }

        @Override
        public void sortFilteredIncomeListByName() {

        }

        @Override
        public void sortFilteredIncomeListByDate() {

        }

        @Override
        public void sortReverseFilteredIncomeListByName() {

        }

        @Override
        public void sortReverseFilteredIncomeListByDate() {

        }

        @Override
        public ReadOnlyFinSec getFinSec() {
            return new FinSec();
        }
    }

}
