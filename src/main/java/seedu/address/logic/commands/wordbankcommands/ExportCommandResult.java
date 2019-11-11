// @@author chrischenhui
package seedu.address.logic.commands.wordbankcommands;

import java.nio.file.Path;

import seedu.address.storage.Storage;

/**
 * Represents the command result returned by {@code RemoveCommand}.
 * This class is needed to pass some info to the {@code LogicManager} to update storage.
 */
public class ExportCommandResult extends WordBankCommandResult {

    ExportCommandResult(String feedback, String wordBankName, Path filePath) {
        super(feedback, wordBankName, filePath);
    }

    @Override
    public void updateStorage(Storage storage) {
        storage.exportWordBank(super.wordBankName, filePath);
    }
}
