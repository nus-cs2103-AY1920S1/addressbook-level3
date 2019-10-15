package seedu.ichifund.logic.commands.transaction;

import seedu.ichifund.commons.core.Messages;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.transaction.Transaction;

import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.*;

public class FilterTransactionCommand extends Command {

    public static final String COMMAND_WORD = "filtertx";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all transactions from a specified "
            + "month, year and optionally category, and displays them as a list with index numbers.\n"
            + "Parameters: "
            + PREFIX_MONTH + "MONTH "
            + PREFIX_YEAR + "YEAR "
            + "[" + PREFIX_CATEGORY + "CATEGORY] "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_MONTH + "10 "
            + PREFIX_YEAR + "2019 "
            + PREFIX_CATEGORY + "food ";

    private final Predicate<Transaction> predicate;

    public FilterTransactionCommand(Predicate<Transaction> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredTransactionList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_TRANSACTIONS_LISTED_OVERVIEW,
                        model.getFilteredTransactionList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FilterTransactionCommand // instanceof handles nulls
                && predicate.equals(((FilterTransactionCommand) other).predicate)); // state check
    }
}
