package seedu.address.transaction.commands;

import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.Ui;

public class AddCommand extends Command {
    private Transaction transaction;
    public static final String COMMAND_WORD = "add";

    public AddCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws Exception {
        Ui ui = new Ui();
        model.addTransaction(transaction);
        model.writeInFile();
        return new CommandResult(ui.addedTransaction(transaction));
    }
}
