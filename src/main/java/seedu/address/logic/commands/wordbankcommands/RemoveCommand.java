// @@author chrischenhui
package seedu.address.logic.commands.wordbankcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wordbank.WordBank;

/**
 * Removes a word bank identified using it's unique name.
 */
public class RemoveCommand extends WordBankCommand {

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + " WORDBANK\n"
            + "Eg: " + COMMAND_WORD + " sample";

    private static final String MESSAGE_REMOVE_CARD_SUCCESS = "Removed word bank: %1$s";

    private static String wordBankName;

    public RemoveCommand(String wordBankName) {
        this.wordBankName = wordBankName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.hasWordBank(wordBankName)) {
            WordBank wb = model.getWordBankFromName(wordBankName);
            model.updateWordBank(wordBankName);
            return new RemoveCommandResult(String.format(MESSAGE_REMOVE_CARD_SUCCESS, wordBankName), wordBankName);
        } else {
            throw new CommandException(Messages.MESSAGE_INVALID_WORD_BANK_NAME);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                // instanceof handles nulls
                || (other instanceof seedu.address.logic.commands.wordbankcommands.RemoveCommand
                && wordBankName
                .equals(((seedu.address.logic.commands.wordbankcommands.RemoveCommand) other).wordBankName));
    }

    public String getWordBankName() {
        return wordBankName;
    }
}
