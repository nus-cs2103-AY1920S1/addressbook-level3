package seedu.address.reimbursement.logic;

import seedu.address.reimbursement.commands.Command;
import seedu.address.reimbursement.commands.CommandResult;
import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.model.ReimbursementList;

/**
 * Implements the logic for Reimbursements.
 */
public class LogicManager implements Logic {

    private final seedu.address.reimbursement.model.Model reimbursementModel;
    private final seedu.address.reimbursement.storage.StorageManager reimbursementStorage;
    private final seedu.address.person.model.Model personModel;
    private final seedu.address.transaction.model.Model transactionModel;
    private final seedu.address.transaction.storage.StorageManager transactionStorage;
    private ReimbursementTabParser parser;


    public LogicManager(Model reimbursementModel,
                        seedu.address.reimbursement.storage.StorageManager reimbursementStorage,
                        seedu.address.transaction.model.Model transactionModel,
                        seedu.address.transaction.storage.StorageManager transactionStorage,
                        seedu.address.person.model.Model personModel) throws Exception {

        this.reimbursementModel = reimbursementModel;
        this.reimbursementStorage = reimbursementStorage;

        this.parser = new ReimbursementTabParser();

        this.personModel = personModel;

        this.transactionModel = transactionModel;
        this.transactionStorage = transactionStorage;

        reimbursementModel.updateReimbursementList(transactionModel.getTransactionList());
        reimbursementStorage.writeFile(reimbursementModel.getReimbursementList());
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        Command command = parser.parseCommand(commandText, personModel);
        CommandResult commandResult = command.execute(reimbursementModel, personModel);
        transactionStorage.writeFile(transactionModel.getTransactionList());
        reimbursementStorage.writeFile(reimbursementStorage.getReimbursementList());
        return commandResult;
    }

    @Override
    public ReimbursementList getReimbursementListFromFile() throws Exception {
        return this.reimbursementStorage.getReimbursementList();
    }

    @Override
    public void writeIntoReimbursementFile() throws Exception {
        reimbursementModel.writeInReimbursementFile();
    }

    @Override
    public ReimbursementList getReimbursementList() {
        return reimbursementModel.getReimbursementList();
    }

}
