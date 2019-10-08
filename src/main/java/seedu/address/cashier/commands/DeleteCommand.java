package seedu.address.cashier.commands;

import seedu.address.cashier.ui.CashierUi;
import seedu.address.inventory.Item;
import seedu.address.transaction.commands.CommandResult;
import seedu.address.cashier.model.Model;

public class DeleteCommand extends Command {
    private int index;
    public static final String COMMAND_WORD = "delete";

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) throws Exception {
        CashierUi cashierUi = new CashierUi();
        Item item = model.findItemByIndex(index);
        model.deleteItem(index);
        return new CommandResult(cashierUi.deletedItem(item));
    }
}
