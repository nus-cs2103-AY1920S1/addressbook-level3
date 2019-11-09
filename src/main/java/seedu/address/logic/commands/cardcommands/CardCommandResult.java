// @@author chrischenhui
package seedu.address.logic.commands.cardcommands;

import seedu.address.logic.commands.CommandResult;
import seedu.address.model.card.Card;
import seedu.address.model.wordbank.WordBank;
import seedu.address.storage.Storage;

/**
 * Represents the command result returned by a card command.
 * This class is needed to pass some info to the {@code LogicManager} to update storage.
 */
public class CardCommandResult extends CommandResult {
    protected Card card;

    CardCommandResult(String feedback) {
        super(feedback);
    }

    public void updateStorage(Storage storage, WordBank wordBank) {
        storage.updateWordBank(wordBank);
    }
}
