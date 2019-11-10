// @@author chrischenhui
package seedu.address.logic.commands.wordbankcommands;

import seedu.address.storage.Storage;

/**
 * Represents the command result returned by {@code CreateCommand}.
 * This class is needed to pass some info to the {@code LogicManager} to update storage.
 */
public class CreateCommandResult extends WordBankCommandResult {

    CreateCommandResult(String feedback, String wordBankName) {
        super(feedback, wordBankName);
    }

    @Override
    public void updateStorage(Storage storage) {
        storage.createWordBank(super.wordBankName);
    }
}
