package seedu.ichifund.logic.parser.budget;

import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;

import java.util.stream.Stream;

import seedu.ichifund.logic.commands.budget.AddBudgetCommand;
import seedu.ichifund.logic.parser.ArgumentMultimap;
import seedu.ichifund.logic.parser.ArgumentTokenizer;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.Prefix;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.Amount;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.budget.Budget;

/**
 * Parses input arguments and creates a new AddBudgetCommand object
 */
public class AddBudgetCommandParser implements Parser<AddBudgetCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddBudgetCommand
     * and returns an AddBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddBudgetCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_AMOUNT);

        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_AMOUNT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddBudgetCommand.MESSAGE_USAGE));
        }

        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        Budget budget = new Budget(description, amount);

        return new AddBudgetCommand(budget);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
