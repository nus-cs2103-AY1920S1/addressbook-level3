package seedu.address.reimbursement.logic;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.address.person.model.UserPrefs;
import seedu.address.reimbursement.model.ModelManager;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.reimbursement.storage.StorageManager;
import seedu.address.testutil.TypicalPersons;
import seedu.address.testutil.TypicalReimbursements;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

public class LogicManagerTest {

    private File file;
    private File tFile;

    private seedu.address.reimbursement.model.Model reimbursementModel;
    private seedu.address.reimbursement.storage.StorageManager reimbursementStorage;
    private seedu.address.person.model.Model personModel;
    private seedu.address.transaction.model.Model transactionModel;
    private seedu.address.transaction.storage.Storage transactionStorage;

    private Logic logic;

    public LogicManagerTest() throws Exception {
        try {
            reimbursementModel = new ModelManager(TypicalReimbursements.getTypicalReimbursements());
            personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());
            file = File.createTempFile("testingLogic", "tempReimbursement.txt");
            reimbursementStorage = new StorageManager(file);
            tFile = File.createTempFile("testingLogic", "tempTransaction.txt");
            transactionStorage =
                    new seedu.address.transaction.storage.StorageManager(tFile, personModel);
            transactionModel = new seedu.address.transaction.model.ModelManager(TypicalReimbursements.getTypicalTransactions());
            logic =
                    new LogicManager(reimbursementModel, reimbursementStorage, transactionModel, transactionStorage,
                            personModel);
        } catch (IOException e) {
            throw new AssertionError("This method should not throw an exception.");
        }
    }

    @Test
    public void get_filtered_list() {
        assertEquals(reimbursementModel.getFilteredReimbursementList(), logic.getFilteredList());
    }

    @Test
    public void update_reimbursement_from_transaction() {

        assertEquals(reimbursementModel.getReimbursementList(), logic.getFilteredList());

        transactionModel.addTransaction(TypicalTransactions.BOB_TRANSACTION_13);
        try {
            logic.updateReimbursementFromTransaction();
        } catch (IOException e) {
            fail();
        }
        ReimbursementList reimbursementList = new ReimbursementList(transactionModel.getTransactionList());

        assertEquals(reimbursementList.toString(), logic.getFilteredList().toString());
    }


}
