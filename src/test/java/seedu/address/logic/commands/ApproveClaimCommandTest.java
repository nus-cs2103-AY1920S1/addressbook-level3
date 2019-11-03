package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.FinSec;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.claim.ApprovedClaim;
import seedu.address.model.claim.Claim;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.commonvariables.Name;
import seedu.address.model.contact.Contact;
import seedu.address.model.income.Income;
import seedu.address.testutil.ClaimBuilder;

public class ApproveClaimCommandTest {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ApproveClaimCommand(null));
    }

    @Test
    public void execute_indexAcceptedByModel_approveSuccessful() throws Exception {
        ModelStubWithPendingClaim modelStub = new ModelStubWithPendingClaim();
        Index validIndex = Index.fromZeroBased(0);
        Claim claimToApprove = modelStub.getClaim();

        CommandResult commandResult = new ApproveClaimCommand(validIndex).execute(modelStub);

        assertEquals(String.format(ApproveClaimCommand.MESSAGE_APPROVE_CLAIM_SUCCESS, claimToApprove),
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_cannotApproveClaim_throwsCommandException() {
        ModelStubRejectingApproveClaim modelStub = new ModelStubRejectingApproveClaim();
        Index validIndex = Index.fromZeroBased(0);

        assertThrows(CommandException.class,
                Messages.MESSAGE_CANNOT_BE_APPROVED, () -> new ApproveClaimCommand(validIndex).execute(modelStub));
    }

    @Test
    public void equals() {
        Index index1 = Index.fromZeroBased(0);
        Index index2 = Index.fromZeroBased(1);
        ApproveClaimCommand approveClaimCommand1 = new ApproveClaimCommand(index1);
        ApproveClaimCommand approveClaimCommand2 = new ApproveClaimCommand(index2);;

        // same object -> returns true
        assertEquals(approveClaimCommand1, approveClaimCommand1);

        // same values -> returns true
        ApproveClaimCommand approveClaimCommandCopy = new ApproveClaimCommand(index1);
        assertEquals(approveClaimCommand1, approveClaimCommandCopy);

        // different types -> returns false
        assertNotEquals(1, approveClaimCommand1);

        // null -> returns false
        assertNotEquals(null, approveClaimCommand1);

        // different claims -> returns false
        assertNotEquals(approveClaimCommand1, approveClaimCommand2);
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
        public void sortFilteredContactListByName() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortReverseFilteredContactListByName() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredClaimListByName() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredClaimListByDate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredClaimListByStatus() {

        }

        @Override
        public void sortReverseFilteredClaimListByName() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortReverseFilteredClaimListByDate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortReverseFilteredClaimListByStatus() {

        }

        @Override
        public void sortFilteredIncomeListByName() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredIncomeListByDate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortReverseFilteredIncomeListByName() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortReverseFilteredIncomeListByDate() {
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
        public void updateFilteredContactList(Predicate<Contact> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Claim> getFilteredClaimList() {
            throw new AssertionError("This method should not be called.");
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
    private class ModelStubWithPendingClaim extends ModelStub {
        private final Claim claim = new ClaimBuilder().build();
        private final FinSecStub finSec = new FinSecStub();

        ModelStubWithPendingClaim() {
            addClaim(claim);
        }

        Claim getClaim() {
            return claim;
        }

        @Override
        public void addClaim(Claim claim) {
            finSec.addClaim(claim);
        }

        @Override
        public ObservableList<Claim> getFilteredClaimList() {
            return finSec.getClaimList();
        }

        @Override
        public void approveClaim(Claim claim) {
            finSec.approveClaim(claim);
        }
    }

    /**
     * A Model stub that contain a claim that cannot be approved.
     */
    private class ModelStubRejectingApproveClaim extends ModelStub {
        private final Claim claim = new ClaimBuilder().buildApproved();
        private final FinSecStub finSec = new FinSecStub();

        ModelStubRejectingApproveClaim() {
            addClaim(claim);
        }

        @Override
        public void addClaim(Claim claim) {
            finSec.addClaim(claim);
        }

        @Override
        public ObservableList<Claim> getFilteredClaimList() {
            return finSec.getClaimList();
        }

        @Override
        public void approveClaim(Claim claim) {
            finSec.approveClaim(claim);
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
            throw new AssertionError("This method should not be called.");
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
            int index = claims.indexOf(target);
            claims.set(index, new ApprovedClaim(target));
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
