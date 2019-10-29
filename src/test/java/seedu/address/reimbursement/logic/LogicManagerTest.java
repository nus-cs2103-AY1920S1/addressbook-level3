package seedu.address.reimbursement.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.UserPrefs;
import seedu.address.reimbursement.model.ModelManager;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.reimbursement.storage.StorageManager;
import seedu.address.testutil.TypicalReimbursements;
import seedu.address.testutil.TypicalTransactions;
import seedu.address.transaction.model.Model;

public class LogicManagerTest {

    private File file;
    private File tFile;

    private seedu.address.reimbursement.model.Model reimbursementModel;
    private seedu.address.reimbursement.storage.StorageManager reimbursementStorage;
    private seedu.address.person.model.Model personModel;
    private Model transactionModel;

    private Logic logic;

    public LogicManagerTest() {
        try {

            reimbursementModel = new ModelManager(TypicalReimbursements.getTypicalReimbursements());
            personModel = new seedu.address.person.model.ModelManager(getTypicalAddressBook(), new UserPrefs());
            file = File.createTempFile("testingLogic", "tempReimbursement.txt");
            reimbursementStorage = new StorageManager(file);
            tFile = File.createTempFile("testingLogic", "tempTransaction.txt");
            transactionModel =
                    new seedu.address.transaction.model.ModelManager(TypicalReimbursements.getTypicalTransactions());
            logic =
                    new LogicManager(reimbursementModel, reimbursementStorage, personModel);
        } catch (IOException e) {
            throw new AssertionError("This method should not throw an exception.");
        }
    }

    @Test
    public void getFilteredList() {
        assertEquals(reimbursementModel.getFilteredReimbursementList(), logic.getFilteredList());
    }

    @Test
    public void updateReimbursementFromTransaction() {

        assertEquals(reimbursementModel.getReimbursementList(), logic.getFilteredList());

        transactionModel.addTransaction(TypicalTransactions.BOB_TRANSACTION_13);
        try {
            logic.updateReimbursementFromTransaction(transactionModel.getTransactionList());
        } catch (IOException e) {
            fail();
        }
        ReimbursementList reimbursementList = new ReimbursementList(transactionModel.getTransactionList());

        assertEquals(reimbursementList.toString(), logic.getFilteredList().toString());
    }


}
