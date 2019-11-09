package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.Filters.FIRST_FILTER;
import static seedu.address.testutil.Filters.SECOND_FILTER;

import java.nio.file.Path;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyFinSec;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.SortFilter;
import seedu.address.model.autocorrectsuggestion.AutocorrectSuggestion;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.UniqueClaimsList;
import seedu.address.model.commanditem.CommandItem;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.UniqueContactsList;
import seedu.address.model.income.Income;
import seedu.address.model.income.UniqueIncomesList;
import seedu.address.ui.UiManager;

class SortCommandTest {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortCommand(null));
    }

    @Test
    public void execute_validContactFilterName() throws CommandException {
        UiManager.changeState("contacts");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("name", 1);
        CommandResult commandResult = new SortCommand(validFilter).execute(model);
        assertEquals("Contacts List Sorted in Lexicographical Order", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validClaimFilterName() throws CommandException {
        UiManager.changeState("claims");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("name", 1);
        CommandResult commandResult = new SortCommand(validFilter).execute(model);
        assertEquals("Claims List Sorted in Lexicographical Order", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validClaimFilterDate() throws CommandException {
        UiManager.changeState("claims");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("date", 2);
        CommandResult commandResult = new SortCommand(validFilter).execute(model);
        assertEquals("Claims list sorted from Oldest Entry to Newest", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validClaimFilterStatus() throws CommandException {
        UiManager.changeState("claims");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("status", 3);
        CommandResult commandResult = new SortCommand(validFilter).execute(model);
        assertEquals("Claims list sorted from Pending to Approved to Rejected", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validIncomeFilterName() throws CommandException {
        UiManager.changeState("incomes");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("name", 1);
        CommandResult commandResult = new SortCommand(validFilter).execute(model);
        assertEquals("Incomes List Sorted in Lexicographical Order", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validIncomeFilterDate() throws CommandException {
        UiManager.changeState("incomes");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("date", 2);
        CommandResult commandResult = new SortCommand(validFilter).execute(model);
        assertEquals("Incomes list sorted from Oldest Entry to Newest", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidFilter() {
        SortFilter invalidFilter;
        try {
            invalidFilter = new SortFilter("invalid", 4);
        } catch (IllegalArgumentException e) {
            invalidFilter = null;
            SortFilter finalInvalidFilter = invalidFilter;
            assertThrows(NullPointerException.class, () -> new SortCommand(finalInvalidFilter));
        }
    }

    @Test
    public void equals() {
        SortCommand sortFirstCommand = new SortCommand(FIRST_FILTER);
        SortCommand sortSecondCommand = new SortCommand(SECOND_FILTER);
        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        //        // same values -> returns true
        //        SortCommand sortFirstCommandCopy = new SortCommand(FIRST_FILTER);
        //        assertEquals(sortFirstCommand, sortFirstCommandCopy);

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
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
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortReverseFilteredContactListByName() {
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
        public void sortFilteredClaimListByName() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredClaimListByDate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredClaimListByStatus() {
            throw new AssertionError("This method should not be called.");
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
     * Returns a modelstub that has all the sort methods working
     */
    private class ModelStubForValidList extends ModelStub {
        private UniqueIncomesList incomesList = new UniqueIncomesList();
        private FilteredList<Income> filteredIncomes = new FilteredList<>(incomesList.asUnmodifiableObservableList());
        private UniqueContactsList contactsList = new UniqueContactsList();
        private FilteredList<Contact> filteredContacts =
                new FilteredList<>(contactsList.asUnmodifiableObservableList());
        private UniqueClaimsList claimsList = new UniqueClaimsList();
        private FilteredList<Claim> filteredClaims = new FilteredList<>(claimsList.asUnmodifiableObservableList());

        //====== Update methods =======
        @Override
        public void updateFilteredClaimList(Predicate<Claim> predicate) {
            filteredClaims.setPredicate(p -> true);
        }

        @Override
        public void updateFilteredIncomeList(Predicate<Income> predicate) {
            filteredIncomes.setPredicate(p -> true);
        }

        @Override
        public void updateFilteredContactList(Predicate<Contact> predicate) {
            filteredContacts.setPredicate(p -> true);
        }

        @Override
        public void sortFilteredContactListByName() {
            filteredContacts = new FilteredList<>(filteredContacts);
            updateFilteredContactList(p -> true);
        }

        @Override
        public void sortFilteredClaimListByName() {
            filteredClaims = new FilteredList<>(filteredClaims);
            updateFilteredClaimList(p -> true);
        }

        @Override
        public void sortFilteredClaimListByDate() {
            filteredClaims = new FilteredList<>(filteredClaims);
            updateFilteredClaimList(p -> true);
        }

        @Override
        public void sortFilteredClaimListByStatus() {
            filteredClaims = new FilteredList<>(filteredClaims);
            updateFilteredClaimList(p -> true);
        }

        @Override
        public void sortFilteredIncomeListByName() {
            filteredIncomes = new FilteredList<>(filteredIncomes);
            updateFilteredIncomeList(p -> true);
        }

        @Override
        public void sortFilteredIncomeListByDate() {
            filteredIncomes = new FilteredList<>(filteredIncomes);
            updateFilteredIncomeList(p -> true);
        }
    }
}

