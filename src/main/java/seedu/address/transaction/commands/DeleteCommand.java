package seedu.address.transaction.commands;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.Ui;

public class DeleteCommand extends Command {
    private int index;
    public static final String COMMAND_WORD = "delete";

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model) throws Exception {
        Ui ui = new Ui();
        Transaction transaction = model.findTransactionByIndex(index);
        model.deleteTransaction(index);
        model.writeInFile();
        return new CommandResult(ui.deletedTransaction(transaction));
    }
}
