package seedu.address.transaction.commands;

import seedu.address.transaction.commands.exception.NoSuchPersonException;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.MyUi;

public class AddCommand extends Command {
    private Transaction transaction;
    public static final String COMMAND_WORD = "add";

    public AddCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception {
        MyUi myUi = new MyUi();
        model.addTransaction(transaction);
        if (!personModel.hasPerson(transaction.getPerson())) {
           //personModel.addPerson(transaction.getPerson());
            throw new NoSuchPersonException();
        }
        return new CommandResult(myUi.addedTransaction(transaction));
    }
}
