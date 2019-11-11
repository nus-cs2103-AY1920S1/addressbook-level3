package seedu.address.reimbursement.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import seedu.address.person.model.ModelManager;
import seedu.address.person.model.UserPrefs;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.reimbursement.storage.StorageManager;
import seedu.address.testutil.TypicalReimbursements;
import seedu.address.transaction.storage.exception.FileReadException;

public class LogicManagerTest {

    private File file;

    private seedu.address.reimbursement.model.Model reimbursementModel;
    private seedu.address.reimbursement.storage.StorageManager reimbursementStorage;
    private seedu.address.person.model.Model personModel;
    private seedu.address.transaction.model.Model transactionModel;

    private TypicalReimbursements typicalReimbursements = new TypicalReimbursements();

    private Logic logic;

    public LogicManagerTest() {
        typicalReimbursements.resetReimbursements();

        try {
            reimbursementModel =
                    new seedu.address.reimbursement.model.ModelManager(
                            typicalReimbursements.getTypicalReimbursements());
            personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
            file = File.createTempFile("testingLogic", "tempReimbursement.txt");
            reimbursementStorage = new StorageManager(file);
            transactionModel =
                    new seedu.address.transaction.model.ModelManager(typicalReimbursements.getTypicalTransactions());
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

        transactionModel.addTransaction(typicalReimbursements.getBobTransaction13());
        try {
            logic.updateReimbursementModelAndStorage(transactionModel.getTransactionList());
        } catch (IOException | FileReadException e) {
            fail();
        }
        ReimbursementList reimbursementList = new ReimbursementList(transactionModel.getTransactionList());

        assertEquals(reimbursementList.toString(), logic.getFilteredList().toString());
    }

}
