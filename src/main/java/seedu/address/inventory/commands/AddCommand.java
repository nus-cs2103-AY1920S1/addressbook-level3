package seedu.address.inventory.commands;

import seedu.address.inventory.commands.exception.NoSuchPersonException;
import seedu.address.inventory.model.Item;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.Transaction;
import seedu.address.transaction.ui.TransactionUi;

public class AddCommand extends Command {
    private Item item;
    public static final String COMMAND_WORD = "add";

    public AddCommand(Item item) {
        this.item = item;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception {
        TransactionUi transactionUi = new TransactionUi();
        if (!personModel.hasPerson(transaction.getPerson())) {
           //personModel.addPerson(transaction.getPerson());
            throw new NoSuchPersonException();
        }
        model.addTransaction(transaction);
        return new CommandResult(transactionUi.addedTransaction(transaction));
    }
}
