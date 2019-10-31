package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

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


public class AddContactCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddContactCommand(null));
    }

    //    @Test
    //    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
    //        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
    //        Contact validPerson = new ContactBuilder().build();
    //
    //        CommandResult commandResult = new AddContactCommand(validPerson).execute(modelStub);
    //
    //        assertEquals(String.format(AddContactCommand.MESSAGE_SUCCESS, validPerson),
    //        commandResult.getFeedbackToUser());
    //        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    //    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Contact validPerson = new ContactBuilder().build();
        AddContactCommand addContactCommand = new AddContactCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

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
        assertEquals(addAliceCommand, addAliceCommand);

        // same values -> returns true
        AddContactCommand addAliceCommandCopy = new AddContactCommand(alice);
        assertEquals(addAliceCommand, addAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addAliceCommand);

        // null -> returns false
        assertNotEquals(null, addAliceCommand);

        // different person -> returns false
        assertNotEquals(addAliceCommand, addBobCommand);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
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
        public ReadOnlyFinSec getFinSec() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setFinSec(ReadOnlyFinSec finSec) {
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
        public void addContact(Contact contact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClaim(Claim claim) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteClaim(Claim target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClaim(Claim claim) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void approveClaim(Claim claim) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void rejectClaim(Claim claim) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setContact(Contact target, Contact editedContact) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setClaim(Claim target, Claim editedClaim) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasContactFor(Claim target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasIncome(Income income) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteIncome(Income target) {
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
        public boolean hasAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
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
        public boolean hasCommand(CommandItem command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCommand(CommandItem command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCommand(CommandItem command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCommand(CommandItem command, CommandItem editedCommand) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveCommand(String command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getSavedCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Contact> getFilteredContactList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredContactListByName() {

        }

        @Override
        public void sortReverseFilteredContactListByName() {

        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Claim> getFilteredClaimList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredClaimListByName() {

        }

        @Override
        public void sortFilteredClaimListByDate() {

        }

        @Override
        public void sortReverseFilteredClaimListByName() {

        }

        @Override
        public void sortReverseFilteredClaimListByDate() {

        }

        @Override
        public void updateFilteredClaimList(Predicate<Claim> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Income> getFilteredIncomeList() {
            throw new AssertionError("This method should not be called.");
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
        public void updateFilteredIncomeList(Predicate<Income> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<AutocorrectSuggestion> getFilteredAutocorrectSuggestionList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredAutocorrectSuggestionList(Predicate<AutocorrectSuggestion> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<CommandItem> getFilteredCommandsList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCommandsList(Predicate<CommandItem> predicate) {
            throw new AssertionError("This method should not be called.");
        }
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
        public void addAutocorrectSuggestion(AutocorrectSuggestion suggestion) {
            return;
        }

        @Override
        public ObservableList<AutocorrectSuggestion> getFilteredAutocorrectSuggestionList() {
            return new FilteredList<AutocorrectSuggestion>(null);
        }

        @Override
        public ReadOnlyFinSec getFinSec() {
            return new FinSec();
        }
    }

}
