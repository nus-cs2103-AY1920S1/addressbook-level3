package seedu.address.transaction.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.transaction.logic.commands.CommandTestUtil.showTransactionsOfPerson;
import static seedu.address.transaction.ui.TransactionMessages.MESSAGE_RESET_TO_ORIGINAL_ORDER;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalTransactions;

class SortResetCommandTest {

    private seedu.address.transaction.model.Model model =
            new seedu.address.transaction.model.ModelManager(TypicalTransactions.getTypicalTransactionList());
    private CheckAndGetPersonByNameModel personModel =
            new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SortResetCommand().execute(null, null));
    }

    @Test
    void execute_filteredList_success() {
        showTransactionsOfPerson(model, TypicalPersons.ALICE.getName().toString());
        SortResetCommand sortResetCommand = new SortResetCommand();
        String message = String.format(MESSAGE_RESET_TO_ORIGINAL_ORDER);

        assertEquals(sortResetCommand.execute(model, personModel).getFeedbackToUser(), message);
        assertEquals(model.getTransactionList().getOriginal(),
                TypicalTransactions.getTypicalTransactionList().getOriginal());
        assertEquals(model.getTransactionList().getTarrList(),
                TypicalTransactions.getTypicalTransactionList().getTarrList());
    }

    @Test
    void execute_unfilteredList_success() {
        SortResetCommand sortResetCommand = new SortResetCommand();
        //original is random, tArrList is random, but tList is sorted
        String message = String.format(MESSAGE_RESET_TO_ORIGINAL_ORDER);
        assertEquals(sortResetCommand.execute(model, personModel).getFeedbackToUser(), message);
        assertEquals(model.getTransactionList().getOriginal(),
                TypicalTransactions.getTypicalTransactionList().getOriginal());
        assertEquals(model.getTransactionList().getTarrList(),
                TypicalTransactions.getTypicalTransactionList().getTarrList());
    }
}
