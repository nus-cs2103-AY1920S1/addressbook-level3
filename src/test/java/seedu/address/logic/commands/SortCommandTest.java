package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.Filters.FIRST_FILTER;
import static seedu.address.testutil.Filters.SECOND_FILTER;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.transformation.FilteredList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ModelStub;
import seedu.address.model.SortFilter;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.UniqueClaimsList;
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
    public void execute_validContactFilterName_gotoSuccessful() throws CommandException {
        UiManager.changeState("contacts");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("name", 1);
        CommandResult commandResult = new SortCommand(validFilter).execute(model);
        assertEquals("Contacts List Sorted in Lexicographical Order", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidContactFilterDate_gotoSuccessful() throws CommandException {
        UiManager.changeState("contacts");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("date", 2);
        assertThrows(CommandException.class, () -> new SortCommand(validFilter).execute(model));
    }

    @Test
    public void execute_validClaimFilterName_gotoSuccessful() throws CommandException {
        UiManager.changeState("claims");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("name", 1);
        CommandResult commandResult = new SortCommand(validFilter).execute(model);
        assertEquals("Claims List Sorted in Lexicographical Order", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validClaimFilterDate_gotoSuccessful() throws CommandException {
        UiManager.changeState("claims");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("date", 2);
        CommandResult commandResult = new SortCommand(validFilter).execute(model);
        assertEquals("Claims list sorted from Oldest Entry to Newest", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validClaimFilterStatus_gotoSuccessful() throws CommandException {
        UiManager.changeState("claims");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("status", 3);
        CommandResult commandResult = new SortCommand(validFilter).execute(model);
        assertEquals("Claims list sorted from Pending to Approved to Rejected", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validIncomeFilterName_gotoSuccessful() throws CommandException {
        UiManager.changeState("incomes");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("name", 1);
        CommandResult commandResult = new SortCommand(validFilter).execute(model);
        assertEquals("Incomes List Sorted in Lexicographical Order", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validIncomeFilterDate_gotoSuccessful() throws CommandException {
        UiManager.changeState("incomes");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("date", 2);
        CommandResult commandResult = new SortCommand(validFilter).execute(model);
        assertEquals("Incomes list sorted from Oldest Entry to Newest", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIncomeFilterNameWithWrongState_throwsCommandException() throws CommandException {
        UiManager.changeState("asd");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("name", 1);
        assertThrows(CommandException.class, () -> new SortCommand(validFilter).execute(model));
    }

    @Test
    public void execute_invalidIncomeFilterStatus_throwsCommandException() throws CommandException {
        UiManager.changeState("incomes");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("status", 3);
        assertThrows(CommandException.class, () -> new SortCommand(validFilter).execute(model));
    }

    @Test
    public void execute_invalidFilter_throwsCommandException() {
        UiManager.changeState("contacts");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter invalidFilter = new SortFilter("status", 4);
        assertThrows(CommandException.class, () -> new SortCommand(invalidFilter).execute(model));
    }

    @Test
    public void equals() {
        SortCommand sortFirstCommand = new SortCommand(FIRST_FILTER);
        SortCommand sortSecondCommand = new SortCommand(SECOND_FILTER);
        // same object -> returns true
        assertTrue(sortFirstCommand.equals(sortFirstCommand));

        // same values -> returns true
        SortCommand sortFirstCommandCopy = new SortCommand(FIRST_FILTER);
        assertEquals(sortFirstCommand, sortFirstCommandCopy);

        // different types -> returns false
        assertFalse(sortFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(sortFirstCommand.equals(sortSecondCommand));
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

