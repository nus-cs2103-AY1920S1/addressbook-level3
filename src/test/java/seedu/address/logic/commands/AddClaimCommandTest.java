package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;

import java.util.ArrayList;
import java.util.Arrays;
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
import seedu.address.testutil.ClaimBuilder;

public class AddClaimCommandTest {

    @Test
    public void constructor_nullClaim_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddClaimCommand(null));
    }

    @Test
    public void execute_claimAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingClaimAdded modelStub = new ModelStubAcceptingClaimAdded();
        Claim validClaim = new ClaimBuilder().build();

        CommandResult commandResult = new AddClaimCommand(validClaim).execute(modelStub);

        assertEquals(String.format(AddClaimCommand.MESSAGE_SUCCESS, validClaim), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validClaim), modelStub.claimsAdded);
    }

    @Test
    public void execute_duplicateClaim_throwsCommandException() {
        Claim validClaim = new ClaimBuilder().build();
        AddClaimCommand addContactCommand = new AddClaimCommand(validClaim);
        ModelStub modelStub = new ModelStubWithClaim(validClaim);

        assertThrows(CommandException.class,
                AddClaimCommand.MESSAGE_DUPLICATE_CLAIM, () -> addContactCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Claim logistics = new ClaimBuilder().withDescription("Logistics").build();
        Claim transport = new ClaimBuilder().withDescription("Transport").build();
        AddClaimCommand addLogisticsCommand = new AddClaimCommand(logistics);
        AddClaimCommand addTransportCommand = new AddClaimCommand(transport);

        // same object -> returns true
        assertEquals(addLogisticsCommand, addLogisticsCommand);

        // same values -> returns true
        AddClaimCommand addAliceCommandCopy = new AddClaimCommand(logistics);
        assertEquals(addLogisticsCommand, addAliceCommandCopy);

        // different types -> returns false
        assertNotEquals(1, addLogisticsCommand);

        // null -> returns false
        assertNotEquals(null, addLogisticsCommand);

        // different claims -> returns false
        assertNotEquals(addLogisticsCommand, addTransportCommand);
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
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
        public void setFinSec(ReadOnlyFinSec finSec) {
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
     * A Model stub that contains a single claim.
     */
    private class ModelStubWithClaim extends ModelStub {
        private final Claim claim;

        ModelStubWithClaim(Claim claim) {
            requireNonNull(claim);
            this.claim = claim;
        }

        @Override
        public boolean hasClaim(Claim claim) {
            requireNonNull(claim);
            return this.claim.isSameClaim(claim);
        }

        @Override
        public boolean hasContactFor(Claim toAdd) {
            requireNonNull(toAdd);
            return true;
        }
    }

    /**
     * A Model stub that always accept the claim being added.
     */
    private class ModelStubAcceptingClaimAdded extends ModelStub {
        final ArrayList<Claim> claimsAdded = new ArrayList<>();

        @Override
        public boolean hasClaim(Claim claim) {
            requireNonNull(claim);
            return claimsAdded.stream().anyMatch(claim::isSameClaim);
        }

        @Override
        public void addClaim(Claim claim) {
            requireNonNull(claim);
            claimsAdded.add(claim);
        }

        @Override
        public ReadOnlyFinSec getFinSec() {
            return new FinSec();
        }

        @Override
        public boolean hasContactFor(Claim toAdd) {
            requireNonNull(toAdd);
            return true;
        }
    }
}
