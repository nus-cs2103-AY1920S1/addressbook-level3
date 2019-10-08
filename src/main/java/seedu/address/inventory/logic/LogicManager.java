package seedu.address.inventory.logic;

import seedu.address.inventory.commands.Command;
import seedu.address.inventory.commands.CommandResult;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.storage.StorageManager;
import seedu.address.transaction.util.TransactionList;

public class LogicManager implements Logic {

    private Model model;
    private StorageManager storage;
    private HomeTabParser parser;
    private seedu.address.person.storage.Storage personStorage;
    private seedu.address.person.model.Model personModel;

    public LogicManager(Model model, StorageManager storage,
                        seedu.address.person.model.Model personModel, seedu.address.person.storage.Storage personStorage) {
        this.model = model;
        this.storage = storage;
        parser = new HomeTabParser();
        this.personStorage = personStorage;
        this.personModel = personModel;
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        Command command = parser.parseCommand(commandText,
                model.getTransactionList().size());
        CommandResult commandResult = command.execute(model, personModel);
        model.updateIndexes();
        personStorage.saveAddressBook(personModel.getAddressBook());
        storage.writeFile(model.getTransactionList());
        return commandResult;
    }

    public TransactionList getTransactionList() throws Exception {
        return this.storage.getTransactionList();
    }
}
