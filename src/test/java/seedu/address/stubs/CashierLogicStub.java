package seedu.address.stubs;

import java.util.ArrayList;

import seedu.address.cashier.logic.Logic;
import seedu.address.cashier.logic.commands.Command;
import seedu.address.cashier.logic.commands.exception.NoCashierFoundException;
import seedu.address.cashier.logic.parser.CashierTabParser;
import seedu.address.cashier.model.ModelManager;
import seedu.address.cashier.util.InventoryList;
import seedu.address.inventory.model.Item;
import seedu.address.person.model.CheckAndGetPersonByNameModel;
import seedu.address.util.CommandResult;

/**
 * Represents a Cashier tab's Logic stub.
 */
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
                model, (CheckAndGetPersonByNameModel) personModel);
        CommandResult commandResult = command.execute(model, (CheckAndGetPersonByNameModel) personModel);
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
    public void writeInInventoryFile() throws Exception {

    }

    @Override
    public void readInUpdatedList() throws Exception {

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
