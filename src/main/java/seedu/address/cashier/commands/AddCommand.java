package seedu.address.cashier.commands;


import seedu.address.cashier.model.Model;
import seedu.address.cashier.model.exception.NoSuchItemException;
import seedu.address.cashier.ui.CashierUi;
import seedu.address.inventory.Item;

public class AddCommand extends Command {
    private Item item;
    public static final String COMMAND_WORD = "add";

    public AddCommand(Item item) {
        this.item = item;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception {
        CashierUi cashierUi = new CashierUi();
        if (!personModel.hasPerson(transaction.getPerson())) {
            //personModel.addPerson(transaction.getPerson());
            throw new NoSuchItemException();
        }
        model.addItem(item);
        return new CommandResult(CashierUi.addedItem(item));
    }
}

