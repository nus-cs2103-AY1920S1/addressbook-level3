package seedu.address.stubs;

import java.io.IOException;

import seedu.address.transaction.commands.Command;
import seedu.address.transaction.commands.CommandResult;
import seedu.address.transaction.logic.Logic;
import seedu.address.transaction.logic.TransactionTabParser;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.util.TransactionList;

public class TransactionLogicStub implements Logic {
    private Model model;
    private seedu.address.person.model.Model personModel;

    public TransactionLogicStub(Model model, seedu.address.person.model.Model personModel) {
        this.model = model;
        this.personModel = personModel;
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        TransactionTabParser parser = new TransactionTabParser();
        Command command = parser.parseCommand(commandText,
                model.getTransactionList().size(), personModel);
        CommandResult commandResult = command.execute(model, personModel);
        return commandResult;
    }

    @Override
    public void writeIntoTransactionFile() throws IOException {
        throw new AssertionError("This method should not be called.");
    }

    @Override
    public void setTransaction(Transaction transaction, Transaction newTransaction) {

    }

    @Override
    public TransactionList getTransactionList() {
        return null;
    }

    @Override
    public TransactionList getFilteredList() {
        return null;
    }

    @Override
    public void addTransaction(Transaction transaction) {

    }
}
