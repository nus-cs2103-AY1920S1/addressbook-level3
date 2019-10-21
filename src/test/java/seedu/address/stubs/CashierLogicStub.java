package seedu.address.stubs;

import java.util.ArrayList;

import seedu.address.cashier.commands.Command;
import seedu.address.cashier.commands.CommandResult;
import seedu.address.cashier.logic.CashierTabParser;
import seedu.address.cashier.logic.Logic;
import seedu.address.cashier.logic.exception.NoCashierFoundException;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;


public class CashierLogicStub implements Logic {
    private ModelManager model;
    private seedu.address.person.model.Model personModel;
    private seedu.address.transaction.model.ModelManager transactionModel;
    private seedu.address.inventory.model.ModelManager inventoryModel;

    public CashierLogicStub(ModelManager model, seedu.address.person.model.Model personModel) {

    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        CashierTabParser parser = new CashierTabParser();
        Command command = parser.parseCommand(commandText,
                model, personModel);
        CommandResult commandResult = command.execute(model, personModel, transactionModel, inventoryModel);
        return commandResult;
    }

    @Override
    public InventoryList getInventoryList() throws Exception {
        return null;
    }

    @Override
    public ArrayList<Item> getSalesList() throws Exception {
        return null;
    }

    @Override
    public String getAmount() {
        return null;
    }

    @Override
    public String getCashier() throws NoCashierFoundException {
        return null;
    }
}
