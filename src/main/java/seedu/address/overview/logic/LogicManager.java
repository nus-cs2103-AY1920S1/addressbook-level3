package seedu.address.overview.logic;

import static seedu.address.overview.ui.OverviewMessages.ACHIEVED_SALES_TARGET;
import static seedu.address.overview.ui.OverviewMessages.EXCEEDED_BUDGET_TARGET;
import static seedu.address.overview.ui.OverviewMessages.EXCEEDED_EXPENSE_TARGET;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import seedu.address.inventory.model.Item;
import seedu.address.inventory.util.InventoryList;
import seedu.address.overview.logic.commands.Command;
import seedu.address.overview.model.Model;
import seedu.address.overview.storage.Storage;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.model.transaction.Transaction;
import seedu.address.util.CommandResult;

/**
 * Manages the logic behind the transaction tab.
 */
public class LogicManager implements Logic {

    private final Model model;
    private final Storage storage;
    private OverviewTabParser parser;
    private final seedu.address.transaction.logic.Logic transactionLogic;
    private final seedu.address.inventory.logic.Logic inventoryLogic;

    public LogicManager(Model overviewModel, Storage overviewStorage,
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
                .filter(transaction -> transaction.isNegative())
                .flatMapToDouble(transaction -> DoubleStream.of(transaction.getAmount()))
                .sum() * -1;
    }

    public double getTotalInventory() {
        Stream<Item> itemStream = inventoryLogic.getInventoryList().stream();
        return itemStream
                .flatMapToDouble(item -> DoubleStream.of(item.getTotalCost()))
                .sum();
    }

    public double getTotalSales() {
        Stream<Transaction> transactionStream = transactionLogic.getTransactionList().stream();
        return transactionStream
                .filter(transaction -> transaction.getCategory().equals("Sales"))
                .flatMapToDouble(transaction -> DoubleStream.of(transaction.getAmount()))
                .sum();
    }

    public double getRemainingBudget() {
        return model.getBudgetTarget() - getTotalExpenses() + getTotalSales();
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

    public List<String> getTransactionCategories() {
        List<String> categoryList = new ArrayList<>();
        TransactionList transactionList = transactionLogic.getTransactionList();

        for (int i = 0; i < transactionList.size(); i++) {
            if (!(transactionList.get(i).getCategory().equals("Sales"))) {
                categoryList.add(transactionList.get(i).getCategory());
            }
        }

        return categoryList.stream().distinct().collect(Collectors.toList());
    }

    public List<String> getInventoryCategories() {
        List<String> categoryList = new ArrayList<>();
        InventoryList inventoryList = inventoryLogic.getInventoryList();

        for (int i = 0; i < inventoryList.size(); i++) {
            categoryList.add(inventoryList.get(i).getCategory());
        }

        return categoryList.stream().distinct().collect(Collectors.toList());
    }

    public double getTransactionTotalByCategory(String category) {
        Stream<Transaction> transactionStream = transactionLogic.getTransactionList().stream();
        return transactionStream
                .filter(transaction -> transaction.getCategory().equals(category))
                .filter(transaction -> transaction.isNegative())
                .flatMapToDouble(transaction -> DoubleStream.of(transaction.getAmount()))
                .sum() * -1;
    }

    public double getInventoryTotalByCategory(String category) {
        Stream<Item> itemStream = inventoryLogic.getInventoryList().stream();
        return itemStream
                .filter(item -> item.getCategory().equals(category))
                .flatMapToDouble(item -> DoubleStream.of(item.getTotalCost()))
                .sum();
    }

    public double getSalesTotalByMonth(LocalDate currentDate) {
        Stream<Transaction> transactionStream = transactionLogic.getTransactionList().stream();
        return transactionStream
                .filter(transaction -> transaction.getDateObject().getMonth() == currentDate.getMonth())
                .filter(transaction -> transaction.getCategory().equals("Sales"))
                .flatMapToDouble(transaction -> DoubleStream.of(transaction.getAmount()))
                .sum();
    }

    public double getBudgetLeftByMonth(LocalDate currentDate) {
        Stream<Transaction> transactionStream = transactionLogic.getTransactionList().stream();
        return getRemainingBudget() - transactionStream
                .filter(transaction -> transaction.getDateObject().getMonth() == currentDate.getMonth())
                .flatMapToDouble(transaction -> DoubleStream.of(transaction.getAmount()))
                .sum();
    }

    /**
     * Checks if the user needs to be notified of his targets.
     * @return A list of CommandResults with the notifications.
     */
    public List<CommandResult> checkNotifications() {
        ArrayList<CommandResult> list = new ArrayList<>();

        if (model.checkBudgetNotif()) {
            checkThreshold(getTotalExpenses(), model.getBudgetTarget(), model.getBudgetThreshold(),
                    EXCEEDED_BUDGET_TARGET).ifPresent(list::add);
            model.setBudgetNotif(false);
        }
        if (model.checkExpenseNotif()) {
            checkThreshold(getTotalExpenses(), model.getExpenseTarget(), model.getExpenseThreshold(),
                    EXCEEDED_EXPENSE_TARGET).ifPresent((list::add));
            model.setExpenseNotif(false);
        }
        if (model.checkSalesNotif()) {
            checkThreshold(getTotalSales(), model.getSalesTarget(), model.getSalesThreshold(),
                    ACHIEVED_SALES_TARGET).ifPresent(list::add);
            model.setSalesNotif(false);
        }

        return list;
    }

    /**
     * Helper method to reduce code duplication.
     * Checks if threshold has been met.
     */
    private Optional<CommandResult> checkThreshold(double amount, double target, double threshold, String message) {
        if (target == 0.0 || threshold == 0.0) {
            return Optional.empty();
        } else if ((amount / target) * 100 >= threshold) {
            return Optional.of(new CommandResult(String.format(message, threshold)));
        } else {
            return Optional.empty();
        }
    }
}
