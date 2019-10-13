package seedu.address.overview.logic;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import seedu.address.overview.commands.Command;
import seedu.address.overview.commands.CommandResult;
import seedu.address.overview.model.Model;
import seedu.address.overview.storage.StorageManager;
import seedu.address.transaction.model.Transaction;

/**
 * Manages the logic behind the transaction tab.
 */
public class LogicManager implements Logic {

    private final Model model;
    private final StorageManager storage;
    private OverviewTabParser parser;
    private final seedu.address.transaction.logic.Logic transactionLogic;
    private final seedu.address.inventory.logic.Logic inventoryLogic;

    public LogicManager(Model overviewModel, StorageManager overviewStorage,
                        seedu.address.transaction.logic.Logic transactionLogic,
                        seedu.address.inventory.logic.Logic inventoryLogic) {
        this.model = overviewModel;
        this.storage = overviewStorage;
        this.parser = new OverviewTabParser();
        this.transactionLogic = transactionLogic;
        this.inventoryLogic = inventoryLogic;

    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        Command command = parser.parseCommand(commandText);
        CommandResult commandResult = command.execute(model);
        storage.writeToFile(model);
        return commandResult;
    }

    public double getTotalExpenses() {
        Stream<Transaction> transactionStream = transactionLogic.getTransactionList().stream();
        return transactionStream
                .filter(transaction -> !transaction.getCategory().equals("Sales"))
                .flatMapToDouble(transaction -> DoubleStream.of(transaction.getAmount()))
                .sum();
    }

    public double getTotalInventory() {
        /*Stream<Item> itemStream = inventoryLogic.getInventoryList().stream();
        return itemStream
                .flatMapToDouble(item -> DoubleStream.of(item.getPrice() * item.getQuantity()))
                .sum();*/
        return 0;
    }

    public double getTotalSales() {
        Stream<Transaction> transactionStream = transactionLogic.getTransactionList().stream();
        return transactionStream
                .filter(transaction -> transaction.getCategory().equals("Sales"))
                .flatMapToDouble(transaction -> DoubleStream.of(transaction.getAmount()))
                .sum();
    }

    public double getRemainingBudget() {
        return model.getBudgetTarget() - getTotalExpenses();
    }

    public double getExpenseTarget() {
        return model.getExpenseTarget();
    }

    public double getSalesTarget() {
        return model.getSalesTarget();
    }

    public double getBudgetTarget() {
        return model.getBudgetTarget();
    }


}
