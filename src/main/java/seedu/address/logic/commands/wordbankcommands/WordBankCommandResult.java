package seedu.address.logic.commands.wordbankcommands;

import java.nio.file.Path;

import seedu.address.logic.commands.CommandResult;
import seedu.address.storage.Storage;

/**
 * Represents the command result returned by a home command.
 * This class is needed to pass some info to the {@code LogicManager} to update storage.
 */
public abstract class WordBankCommandResult extends CommandResult {
    protected String wordBankName;
    protected Path filePath;

    public WordBankCommandResult(String feedback, String name) {
        super(feedback);
        this.wordBankName = name;
    }

    public WordBankCommandResult(String feedback, String name, Path filePath) {
        super(feedback);
        this.wordBankName = name;
        this.filePath = filePath;
    }

    public WordBankCommandResult(String feedback, Path filePath) {
        super(feedback);
        this.filePath = filePath;
    }

    public abstract void updateStorage(Storage storage);

}
