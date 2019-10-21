package seedu.address.logic.commands.loadCommands;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AppCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.LoadCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.wordbank.WordBank;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a card identified using it's displayed index from the word bank.
 */
public class RemoveCommand extends LoadCommand {

    public static final String COMMAND_WORD = "remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the word bank identified by the input.\n"
            + "Example: " + COMMAND_WORD + " sample";

    public static final String MESSAGE_REMOVE_CARD_SUCCESS = "Removed word bank: %1$s";

    public static String wordBankName;

    public RemoveCommand(String wordBankName) {
        this.wordBankName = wordBankName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        WordBank wb = model.getWordBankList().getWordBank(wordBankName);
        if (wb == null) {
            throw new CommandException(Messages.MESSAGE_INVALID_NAME);
        }

        model.removeWordBank();
        return new CommandResult(String.format(MESSAGE_REMOVE_CARD_SUCCESS, wordBankName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof seedu.address.logic.commands.loadCommands.RemoveCommand // instanceof handles nulls
                && wordBankName.equals(((seedu.address.logic.commands.loadCommands.RemoveCommand) other).wordBankName)); // state check
    }
}
