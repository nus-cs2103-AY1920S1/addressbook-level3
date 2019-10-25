package seedu.address.logic.commands.homecommands;

import seedu.address.storage.Storage;

import java.nio.file.Path;

/**
 * Represents the command result returned by {@code ImportCommand}.
 * This class is needed to pass some info to the {@code LogicManager} to update storage.
 */
public class ImportCommandResult extends HomeCommandResult {

    public ImportCommandResult(String feedback, Path filePath) {
        super(feedback, filePath);
    }

    @Override
    public void updateStorage(Storage storage) {
        storage.importWordBank(filePath);
    }
}
