package seedu.address.overview.logic;

import seedu.address.overview.commands.Command;
import seedu.address.overview.commands.CommandResult;
import seedu.address.overview.model.Model;
import seedu.address.overview.model.Transaction;
import seedu.address.overview.storage.StorageManager;
import seedu.address.overview.util.TransactionList;
import seedu.address.ui.Overview;

import java.io.IOException;

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

    @Override
    public void writeToOverviewFile() throws IOException {
        storage.writeToFile(model);
    }

}
