package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SHARE;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.transaction.Split;
import seedu.address.ui.tab.Tab;

/**
 * Splits an amount into smaller different amounts.
 */
public class SplitCommand extends Command {

    public static final String COMMAND_WORD = "split";
    public static final String MESSAGE_SUCCESS = "Split successful";
    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Splits an expenditure between people and add that transaction to the bank account.\n"
        + "Parameters: "
        + PREFIX_AMOUNT + "AMOUNT "
        + PREFIX_DESC + "DESCRIPTION "
        + "[" + PREFIX_NAME + "NAME]..."
        + "[" + PREFIX_SHARE + "SHARE]...\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_AMOUNT + "600 "
        + PREFIX_DESC + "haidilao "
        + PREFIX_NAME + "John Doe "
        + PREFIX_NAME + "John Soe "
        + PREFIX_NAME + "John Moe "
        + PREFIX_SHARE + "1 "
        + PREFIX_SHARE + "2 "
        + PREFIX_SHARE + "3\n"
        + "If number of shares is one more than number of names listed, \n"
        + "first share is taken to be user's share of the expenditure\n";
    public static final String SHARES_FORMAT = "If number of shares is equal to number of names given,"
        + "user is assumed to be excluded from the expenditure.\n"
        + "Else, first share is taken to be the user's share.";
    public static final String MESSAGE_DUPLICATE = "This transaction already exists: %1$s";

    private final Split transaction;

    public SplitCommand(Split transaction) {
        requireNonNull(transaction);
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.has(transaction)) {
            return new CommandResult(String.format(MESSAGE_DUPLICATE, transaction), false, false, Tab.LEDGER);
        } else {
            model.add(transaction);
            model.commitUserState();
            return new CommandResult(String.format(MESSAGE_SUCCESS, transaction), false, false, Tab.LEDGER);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj instanceof SplitCommand) {
            SplitCommand splitCommand = (SplitCommand) obj;
            return transaction.equals(splitCommand.transaction);
        } else {
            return false;
        }
    }
}
