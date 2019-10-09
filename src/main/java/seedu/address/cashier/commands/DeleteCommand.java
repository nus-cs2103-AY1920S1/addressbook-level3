package seedu.address.cashier.commands;

import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.model.exception.NoSuchIndexException;
import seedu.address.cashier.ui.CashierUi;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.Model;

public class DeleteCommand extends Command {
    private int index;
    public static final String COMMAND_WORD = "delete";

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public CommandResult execute(ModelManager modelManager, Model personModel) throws NoSuchIndexException {
        CashierUi cashierUi = new CashierUi();
        Item item = modelManager.findItemByIndex(index);
        modelManager.deleteItem(index);
        return new CommandResult(cashierUi.deletedItem(item));
    }
}
