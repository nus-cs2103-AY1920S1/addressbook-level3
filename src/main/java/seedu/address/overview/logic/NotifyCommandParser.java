package seedu.address.overview.logic;

import java.util.stream.Stream;

import seedu.address.overview.commands.*;
import seedu.address.overview.logic.exception.ParseException;
import seedu.address.overview.model.Model;
import seedu.address.overview.ui.OverviewMessages;

import static seedu.address.overview.logic.CliSyntax.PREFIX_BUDGET;
import static seedu.address.overview.logic.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.overview.logic.CliSyntax.PREFIX_SALES;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class NotifyCommandParser {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public static NotifyCommand parse(String args)
            throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_BUDGET, PREFIX_EXPENSE, PREFIX_SALES);

        if (!arePrefixesPresent(argMultimap, PREFIX_BUDGET, PREFIX_EXPENSE, PREFIX_SALES)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(OverviewMessages.MESSAGE_INVALID_COMMAND_FORMAT);
        }

        if (argMultimap.getValue(PREFIX_BUDGET).isPresent()) {
            return new NotifyBudgetCommand(Integer.parseInt(argMultimap.getValue(PREFIX_BUDGET).get()));
        } else if (argMultimap.getValue(PREFIX_EXPENSE).isPresent()) {
            return new NotifyExpenseCommand(Integer.parseInt(argMultimap.getValue(PREFIX_EXPENSE).get()));
        } else if (argMultimap.getValue(PREFIX_SALES).isPresent()) {
            return new NotifySalesCommand(Integer.parseInt(argMultimap.getValue(PREFIX_SALES).get()));
        } else {
            throw new ParseException(OverviewMessages.MESSAGE_INVALID_COMMAND_FORMAT);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argMultimap.getValue(prefix).isPresent());
    }

}
