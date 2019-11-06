package seedu.address.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.logic.commands.CommandTestUtil.showTransactionsOfPerson;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_SORTED_BY_AMOUNT;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;

class SortAmountCommandTest {
    private seedu.address.transaction.model.Model model =
            new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
    private CheckAndGetPersonByNameModel personModel =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortAmountCommand().execute(null, null));
    }

    @Test
    void execute_filteredList_success() {
        showTransactionsOfPerson(model, TypicalPersons.ALICE.getName().toString());
        SortAmountCommand sortAmountCommand = new SortAmountCommand();
        String message = String.format(MESSAGE_SORTED_BY_AMOUNT);

        assertEquals(sortAmountCommand.execute(model, personModel).getFeedbackToUser(), message);
        assertNotEquals(model.getTransactionList().getOriginal(),
                TypicalTransactions.getAmountSortedTransactionList().getOriginal());
        assertEquals(model.getTransactionList().getTarrList(),
                TypicalTransactions.getAmountSortedTransactionList().getTarrList());
    }

    @Test
    void execute_unfilteredList_success() {
        SortAmountCommand sortAmountCommand = new SortAmountCommand(); //original is random,
        // tArrList is random, but tList is sorted
        String message = String.format(MESSAGE_SORTED_BY_AMOUNT);


        seedu.address.transaction.model.Model expectedModel =
                new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.sortByAmount();
        expectedModel.getFilteredList();
        assertCommandSuccess(sortAmountCommand, model, message,
                expectedModel, personModel);


        assertEquals(sortAmountCommand.execute(model, personModel).getFeedbackToUser(), message);
        assertNotEquals(model.getTransactionList().getOriginal(),
                TypicalTransactions.getAmountSortedTransactionList().getOriginal());
        assertEquals(model.getTransactionList().getTarrList(),
                TypicalTransactions.getAmountSortedTransactionList().getTarrList());
    }
}
