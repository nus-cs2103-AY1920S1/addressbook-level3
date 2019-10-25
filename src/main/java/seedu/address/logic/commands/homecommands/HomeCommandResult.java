package seedu.address.logic.commands.homecommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.storage.Storage;

/**
 * Represents the command result returned by a home command.
 * This class is needed to pass some info to the {@code LogicManager} to update storage.
 */
public abstract class HomeCommandResult extends CommandResult {
    String name;

    public HomeCommandResult(String feedback, String name) {
        super(feedback);
        this.name = name;
    }

    public abstract void updateStorage(Storage storage);

}