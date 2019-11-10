package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.Filters.FIRST_FILTER;
import static seedu.address.testutil.Filters.SECOND_FILTER;
import static seedu.address.testutil.TypicalObjects.getTypicalFinSec;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.transformation.FilteredList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ModelStub;
import seedu.address.model.SortFilter;
import seedu.address.model.UserPrefs;
import seedu.address.model.claim.Claim;
import seedu.address.model.claim.UniqueClaimsList;
import seedu.address.model.contact.Contact;
import seedu.address.model.contact.UniqueContactsList;
import seedu.address.model.income.Income;
import seedu.address.model.income.UniqueIncomesList;
import seedu.address.ui.UiManager;


class SortReverseCommandTest {

    private Model model = new ModelManager(getTypicalFinSec(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortReverseCommand(null));
    }

    @Test
    public void execute_validReverseContactFilterName_gotoSuccessful() throws CommandException {
        UiManager.changeState("contacts");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("name", 1);
        CommandResult commandResult = new SortReverseCommand(validFilter).execute(model);
        assertEquals("Contacts List Sorted in Reverse Order", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validReverseClaimFilterName_gotoSuccessful() throws CommandException {
        UiManager.changeState("claims");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("name", 1);
        CommandResult commandResult = new SortReverseCommand(validFilter).execute(model);
        assertEquals("Claims List Sorted in Reverse Order", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validReverseClaimFilterDate_gotoSuccessful() throws CommandException {
        UiManager.changeState("claims");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("date", 2);
        CommandResult commandResult = new SortReverseCommand(validFilter).execute(model);
        assertEquals("Claims list sorted from Newest Entry to Oldest in Reverse Order",
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validReverseClaimFilterStatus_gotoSuccessful() throws CommandException {
        UiManager.changeState("claims");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("status", 3);
        CommandResult commandResult = new SortReverseCommand(validFilter).execute(model);
        assertEquals("Claims list sorted from Rejected to Approved to Pending",
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validReverseIncomeFilterName_gotoSuccessful() throws CommandException {
        UiManager.changeState("incomes");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("name", 1);
        CommandResult commandResult = new SortReverseCommand(validFilter).execute(model);
        assertEquals("Incomes List Sorted in Reverse Order", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_validReverseIncomeFilterDate_gotoSuccessful() throws CommandException {
        UiManager.changeState("incomes");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("date", 2);
        CommandResult commandResult = new SortReverseCommand(validFilter).execute(model);
        assertEquals("Incomes list sorted from Newest Entry to Oldest in Reverse Order",
                commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_invalidIncomeFilterNameWithWrongState_throwsCommandException() throws CommandException {
        UiManager.changeState("asd");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("name", 1);
        assertThrows(CommandException.class, () -> new SortReverseCommand(validFilter).execute(model));
    }

    @Test
    public void execute_invalidIncomeFilterStatus_throwsCommandException() throws CommandException {
        UiManager.changeState("incomes");
        ModelStubForValidList model = new ModelStubForValidList();
        SortFilter validFilter = new SortFilter("status", 3);
        assertThrows(CommandException.class, () -> new SortReverseCommand(validFilter).execute(model));
    }

    @Test
    public void execute_invalidFilter_throwsNullPointerException() {
        SortFilter invalidFilter;
        try {
            invalidFilter = new SortFilter("invalid", 4);
        } catch (IllegalArgumentException e) {
            invalidFilter = null;
            SortFilter finalInvalidFilter = invalidFilter;
            assertThrows(NullPointerException.class, () -> new SortReverseCommand(finalInvalidFilter));
        }
    }

    @Test
    public void equals() {
        SortReverseCommand sortReverseFirstCommand = new SortReverseCommand(FIRST_FILTER);
        SortReverseCommand sortReverseSecondCommand = new SortReverseCommand(SECOND_FILTER);
        // same object -> returns true
        assertTrue(sortReverseFirstCommand.equals(sortReverseFirstCommand));

        // same values -> returns true
        SortReverseCommand sortReverseFirstCommandCopy = new SortReverseCommand(FIRST_FILTER);
        assertEquals(sortReverseFirstCommand, sortReverseFirstCommandCopy);

        // different types -> returns false
        assertFalse(sortReverseFirstCommand.equals(1));

        // null -> returns false
        assertFalse(sortReverseFirstCommand.equals(null));

        // different contact -> returns false
        assertFalse(sortReverseFirstCommand.equals(sortReverseSecondCommand));
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
        public void sortReverseFilteredContactListByName() {
            filteredContacts = new FilteredList<>(filteredContacts);
            updateFilteredContactList(p -> true);
        }

        @Override
        public void sortReverseFilteredClaimListByName() {
            filteredClaims = new FilteredList<>(filteredClaims);
            updateFilteredClaimList(p -> true);
        }

        @Override
        public void sortReverseFilteredClaimListByDate() {
            filteredClaims = new FilteredList<>(filteredClaims);
            updateFilteredClaimList(p -> true);
        }

        @Override
        public void sortReverseFilteredClaimListByStatus() {
            filteredClaims = new FilteredList<>(filteredClaims);
            updateFilteredClaimList(p -> true);
        }

        @Override
        public void sortReverseFilteredIncomeListByName() {
            filteredIncomes = new FilteredList<>(filteredIncomes);
            updateFilteredIncomeList(p -> true);
        }

        @Override
        public void sortReverseFilteredIncomeListByDate() {
            filteredIncomes = new FilteredList<>(filteredIncomes);
            updateFilteredIncomeList(p -> true);
        }
    }
}
