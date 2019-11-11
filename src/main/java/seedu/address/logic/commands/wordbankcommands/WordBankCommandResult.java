// @@author chrischenhui
package seedu.address.logic.commands.wordbankcommands;

import java.nio.file.Path;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CommandResult;
import seedu.address.storage.Storage;

/**
 * Represents the command result returned by a home command.
 * This class is needed to pass some info to the {@code LogicManager} to update storage.
 */
public abstract class WordBankCommandResult extends CommandResult {
    protected String wordBankName;
    protected Path filePath;

    WordBankCommandResult(String feedback, String name) {
        super(feedback);
        this.wordBankName = name;
    }

    WordBankCommandResult(String feedback, String name, Path filePath) {
        super(feedback);
        this.wordBankName = name;
        this.filePath = filePath;
    }

    WordBankCommandResult(String feedback, Path filePath) {
        super(feedback);
        this.filePath = filePath;
    }

    public WordBankCommandResult(String feedback) {
        super(feedback);
    }

    public abstract void updateStorage(Storage storage) throws DataConversionException, IllegalValueException;

}
