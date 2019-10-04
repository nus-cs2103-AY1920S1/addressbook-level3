package seedu.address.transaction.commands;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionUi;

public class DeleteCommand extends Command {
    private int index;
    public static final String COMMAND_WORD = "delete";

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception {
        TransactionUi transactionUi = new TransactionUi();
        Transaction transaction = model.findTransactionByIndex(index);
        model.deleteTransaction(index);
        return new CommandResult(transactionUi.deletedTransaction(transaction));
    }
}
