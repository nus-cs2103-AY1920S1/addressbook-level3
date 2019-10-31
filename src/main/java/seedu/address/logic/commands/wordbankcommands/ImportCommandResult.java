package seedu.address.logic.commands.wordbankcommands;

import java.nio.file.Path;

import seedu.address.storage.Storage;

/**
 * Represents the command result returned by {@code ImportCommand}.
 * This class is needed to pass some info to the {@code LogicManager} to update storage.
 */
public class ImportCommandResult extends WordBankCommandResult {

    private String wordBankName;

    public ImportCommandResult(String feedback, Path filePath, String wordBankName) {
        super(feedback, filePath);
        this.wordBankName = wordBankName;
    }

    @Override
    public void updateStorage(Storage storage) {
        storage.importWordBank(wordBankName, filePath);
    }
}
