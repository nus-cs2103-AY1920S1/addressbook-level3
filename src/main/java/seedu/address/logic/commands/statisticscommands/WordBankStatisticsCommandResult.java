package seedu.address.logic.commands.statisticscommands;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.wordbankcommands.WordBankCommandResult;
import seedu.address.storage.Storage;

/**
 * Represents {@code CommandResult} returned by {@code WordBankStatisticsCommand}.
 */
public abstract class WordBankStatisticsCommandResult extends WordBankCommandResult {

    public WordBankStatisticsCommandResult(String feedback) {
        super(feedback);
    }

    public abstract void updateStorage(Storage storage) throws DataConversionException, IllegalValueException;
}
