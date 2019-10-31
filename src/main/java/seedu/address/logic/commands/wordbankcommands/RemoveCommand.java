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
        WordBank wb = model.getWordBankFromName(wordBankName);
        if (wb == null) {
            throw new CommandException(Messages.MESSAGE_DUPLICATE_WORD_BANK_NAME);
        }

        model.updateWordBank(wordBankName);
        return new RemoveCommandResult(String.format(MESSAGE_REMOVE_CARD_SUCCESS, wordBankName), wordBankName);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.wordbankcommands.RemoveCommand // instanceof handles nulls
                && wordBankName.equals(((seedu.address.logic.commands.wordbankcommands.RemoveCommand) other).wordBankName));
    }

    public static String getWordBankName() {
        return wordBankName;
    }
}
