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
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception {
        Ui ui = new Ui();
        model.addTransaction(transaction);
        //model.writeInTransactionFile();
        if (!personModel.hasPerson(transaction.getPerson())) {
           personModel.addPerson(transaction.getPerson());
        }
        return new CommandResult(ui.addedTransaction(transaction));
    }
}
