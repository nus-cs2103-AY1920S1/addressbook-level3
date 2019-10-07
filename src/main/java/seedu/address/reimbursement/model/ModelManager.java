package seedu.address.reimbursement.model;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Logger;

import seedu.address.person.commons.core.LogsCenter;
import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.reimbursement.storage.StorageManager;
import seedu.address.transaction.util.TransactionList;

public class ModelManager implements Model {
    private final Logger logger = LogsCenter.getLogger(getClass());
    private final StorageManager storage;
    private ReimbursementList reimbursementList;

    public ModelManager(StorageManager storageManager) {
        this.storage = storageManager;
        this.reimbursementList = storageManager.getReimbursementList();
    }

    @Override
    public ReimbursementList getReimbursementList() {
        return reimbursementList;
    }

    @Override
    public Reimbursement findReimbursement(Person person) throws NoSuchPersonReimbursementException {
        return reimbursementList.findReimbursement(person);
    }

    @Override
    public void sortReimbursementListByName() {
        reimbursementList.sortByName();
    }

    @Override
    public void sortReimbursementListByAmount() {
        reimbursementList.sortByAmount();
    }

    @Override
    public void sortReimbursementListByDeadline() {
        reimbursementList.sortByDeadline();
    }

    @Override
    public Reimbursement addDeadline(Person person, String date) throws Exception {
        reimbursementList.addDeadline(person, date);
        return findReimbursement(person);
    }

    @Override
    public Reimbursement doneReimbursement(Person person) throws NoSuchPersonReimbursementException {
        Reimbursement rmb = findReimbursement(person);
        rmb.done();
        return rmb;
    }

    @Override
    public void writeInReimbursementFile() throws Exception{
        storage.writeFile(reimbursementList);
    }

    @Override
    public void updateReimbursementList(TransactionList transList) {
        reimbursementList = new ReimbursementList(transList);
    }

}
