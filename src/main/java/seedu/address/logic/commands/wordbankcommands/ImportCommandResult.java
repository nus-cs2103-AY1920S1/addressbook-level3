// @@author chrischenhui
package seedu.address.logic.commands.wordbankcommands;

import java.nio.file.Path;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.storage.Storage;

/**
 * Represents the command result returned by {@code ImportCommand}.
 * This class is needed to pass some info to the {@code LogicManager} to update storage.
 */
public class ImportCommandResult extends WordBankCommandResult {

    private String wordBankName;

    ImportCommandResult(String feedback, Path filePath, String wordBankName) {
        super(feedback, filePath);
        this.wordBankName = wordBankName;
    }

    @Override
    public void updateStorage(Storage storage) throws DataConversionException, IllegalValueException {
        storage.importWordBank(wordBankName, filePath);
    }
}
