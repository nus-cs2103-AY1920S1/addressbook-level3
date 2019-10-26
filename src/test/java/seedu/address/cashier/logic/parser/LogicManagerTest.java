package seedu.address.cashier.logic.parser;

import java.io.File;

import seedu.address.transaction.logic.Logic;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.storage.Storage;

public class LogicManagerTest {
    private File file;
    private File rFile;
    private Model model;
    private seedu.address.person.model.Model personModel;
    private Storage storage;
    private seedu.address.reimbursement.storage.Storage reimbursementStorage;
    private seedu.address.reimbursement.model.Model reimbursementModel;
    private Logic logic;

    LogicManagerTest() {
    /*    try {
            model = new ModelManager(TypicalItem.getTypicalInventoryList(),
                    TypicalTransactions.getTypicalTransactionList());
            personModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
            file = File.createTempFile("testing", "tempTransaction.txt");
            storage = new StorageManager(file, personModel);
            rFile = File.createTempFile("testing", "tempReimbursement.txt");
            model.getTransactionList();
            reimbursementStorage =
                    new seedu.address.reimbursement.storage.StorageManager(rFile);
            reimbursementModel =
                    new seedu.address.reimbursement.model.ModelManager(
                            reimbursementStorage.getReimbursementFromFile(model.getTransactionList()));
            logic =
                    new LogicManager(model, storage, personModel, reimbursementModel,
                            reimbursementStorage);
        } catch(IOException e) {
            throw new AssertionError("This method should not throw an exception.");
        }  */
}


}
