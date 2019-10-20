package seedu.address.transaction.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.transaction.commands.CommandTestUtil.showTransactionsOfPerson;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_SORTED_BY_AMOUNT;

import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.ModelManager;

import org.junit.jupiter.api.Test;

class SortAmountCommandTest {
    private final Logger logger = LogsCenter.getLogger(getClass());
    private seedu.address.transaction.model.Model model =
            new ModelManager(TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_filteredList_success() {
        showTransactionsOfPerson(model, TypicalPersons.ALICE.getName().toString());
        SortAmountCommand sortAmountCommand = new SortAmountCommand();
        String message = String.format(MESSAGE_SORTED_BY_AMOUNT);

        assertEquals(sortAmountCommand.execute(model, personModel).getFeedbackToUser(), message);
        assertNotEquals(model.getTransactionList().getOriginal(),
                TypicalTransactions.getAmountSortedTransactionList().getOriginal());
        assertEquals(model.getTransactionList().gettList(),
                TypicalTransactions.getAmountSortedTransactionList().gettList());
    }

    @Test
    void execute_unfilteredList_success() {
        SortAmountCommand sortAmountCommand = new SortAmountCommand(); //original is random, tArrList is random, but tList is sorted
        String message = String.format(MESSAGE_SORTED_BY_AMOUNT);


        seedu.address.transaction.model.Model expectedModel =
                new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.sortByAmount();
        expectedModel.getFilteredList();
        assertCommandSuccess(sortAmountCommand, model, message,
                expectedModel, personModel);


        assertEquals(sortAmountCommand.execute(model, personModel).getFeedbackToUser(), message);
        assertNotEquals(model.getTransactionList().getOriginal(),
                TypicalTransactions.getAmountSortedTransactionList().getOriginal());
        assertEquals(model.getTransactionList().gettList(),
                TypicalTransactions.getAmountSortedTransactionList().gettList());
    }

     /*ModelManager expectedModel = new ModelManager(TypicalTransactions.getTypicalTransactionList());
        showTransactionsOfPerson(expectedModel, TypicalPersons.ALICE.getName().toString());
        expectedModel.sortByAmount();
        //creates a transaction list with original being just ALICE
        expectedModel.getFilteredList(); //still dont really understand why need this

        assertCommandSuccess(sortAmountCommand, model, message,
                expectedModel, personModel);*/

     /*seedu.address.transaction.model.Model expectedModel =
                new ModelManager(TypicalTransactions.getTypicalTransactionList());
        expectedModel.sortByAmount(); //original is random, tArrList is random, but tList is sorted
        //creates an original list with everyone in it that is in the sorted order and tlist is sorted.
        expectedModel.getFilteredList(); //creates a new transaction list from the model tList applying predicate so it is sorted and everything
        //still applying the same predicate and its all now sorted
        assertCommandSuccess(sortAmountCommand, model, message,
                expectedModel, personModel);*/
    //Transaction list original will be different since the constructor parameter was different
}