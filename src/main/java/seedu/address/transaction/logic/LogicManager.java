package seedu.address.transaction.logic;

import seedu.address.transaction.commands.Command;
import seedu.address.transaction.commands.CommandResult;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.storage.StorageManager;

public class LogicManager implements Logic {

    private Model model;
    private StorageManager storage;
    private HomeTabParser parser;

    public LogicManager(Model model, StorageManager storage) {
        this.model = model;
        this.storage = storage;
        parser = new HomeTabParser();
    }

    @Override
    public CommandResult execute(String commandText) throws Exception {
        Command command = parser.parseCommand(commandText, storage);
        return command.execute(model);
    }
}
