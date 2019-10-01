package seedu.address.transaction.logic;

import seedu.address.transaction.commands.Command;
import seedu.address.transaction.commands.CommandResult;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.storage.StorageManager;

public class LogicManager implements Logic {

    private Model model;
    private StorageManager storage;
    private HomeTabParser parser;
    private seedu.address.person.storage.StorageManager personStorage;
    private seedu.address.person.model.Model personModel;

    public LogicManager(Model model, StorageManager storage,
                        seedu.address.person.model.Model personModel, seedu.address.person.storage.StorageManager personStorage) {
        this.model = model;
        this.storage = storage;
        parser = new HomeTabParser();
        this.personStorage = personStorage;
        this.personModel = personModel;
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        Command command = parser.parseCommand(commandText);
        CommandResult commandResult = command.execute(model, personModel);
        personStorage.saveAddressBook(personModel.getAddressBook());
        storage.writeFile(model.getTransactionList());
        return commandResult;
    }
}
