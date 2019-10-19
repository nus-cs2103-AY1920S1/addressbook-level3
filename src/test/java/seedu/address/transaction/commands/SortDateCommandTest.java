package seedu.address.transaction.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.commands.CommandTestUtil.showTransactionsOfPerson;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_SORTED_BY_DATE;

import seedu.address.person.model.Model;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.ModelManager;

import org.junit.jupiter.api.Test;

class SortDateCommandTest {
    private seedu.address.transaction.model.Model model =
            new ModelManager(TypicalTransactions.getTypicalTransactionList());
    private Model personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    void execute_filteredList_success() {
        showTransactionsOfPerson(model, TypicalPersons.ALICE.getName().toString());
        SortDateCommand sortDateCommand = new SortDateCommand();
        String message = String.format(MESSAGE_SORTED_BY_DATE);

        assertEquals(sortDateCommand.execute(model, personModel).getFeedbackToUser(), message);
        assertNotEquals(model.getTransactionList().getOriginal(),
                TypicalTransactions.getDateSortedTransactionList().getOriginal());
        assertEquals(model.getTransactionList().gettList(),
                TypicalTransactions.getDateSortedTransactionList().gettList());
    }

    @Test
    void execute_unfilteredList_success() {
        SortDateCommand sortDateCommand = new SortDateCommand(); //original is random, tArrList is random, but tList is sorted
        String message = String.format(MESSAGE_SORTED_BY_DATE);
        assertEquals(sortDateCommand.execute(model, personModel).getFeedbackToUser(), message);
        assertNotEquals(model.getTransactionList().getOriginal(),
                TypicalTransactions.getDateSortedTransactionList().getOriginal());
        assertEquals(model.getTransactionList().gettList(),
                TypicalTransactions.getDateSortedTransactionList().gettList());
    }
}
