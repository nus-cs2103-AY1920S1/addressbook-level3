package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;

import java.util.stream.Stream;

import seedu.jarvis.logic.commands.finance.PaidCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.ParserUtil;
import seedu.jarvis.logic.parser.Prefix;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.financetracker.purchase.Purchase;
import seedu.jarvis.model.financetracker.purchase.PurchaseDescription;
import seedu.jarvis.model.financetracker.purchase.PurchaseMoneySpent;

/**
 * Parses input argument and creates a new PaidCommand object
 */
public class PaidCommandParser implements Parser<PaidCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PaidCommand
     * and returns an PaidCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PaidCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_MONEY);
        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_MONEY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    PaidCommand.MESSAGE_USAGE));
        }

        String description = ParserUtil.parsePurchaseDes(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        double moneySpent = ParserUtil.parsePurchaseAmount(argMultimap.getValue(PREFIX_MONEY).get());

        Purchase purchase = new Purchase(new PurchaseDescription(description),
                new PurchaseMoneySpent(Double.toString(moneySpent)));

        return new PaidCommand(purchase);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
