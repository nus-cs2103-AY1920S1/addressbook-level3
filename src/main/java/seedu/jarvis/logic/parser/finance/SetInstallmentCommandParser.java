package seedu.jarvis.logic.parser.finance;

import static seedu.jarvis.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_DESCRIPTION;
import static seedu.jarvis.logic.parser.CliSyntax.FinanceSyntax.PREFIX_MONEY;

import java.util.stream.Stream;

import seedu.jarvis.logic.commands.finance.SetInstallmentCommand;
import seedu.jarvis.logic.parser.ArgumentMultimap;
import seedu.jarvis.logic.parser.ArgumentTokenizer;
import seedu.jarvis.logic.parser.Parser;
import seedu.jarvis.logic.parser.Prefix;
import seedu.jarvis.logic.parser.exceptions.ParseException;
import seedu.jarvis.model.financetracker.installment.Installment;
import seedu.jarvis.model.financetracker.installment.InstallmentDescription;
import seedu.jarvis.model.financetracker.installment.InstallmentMoneyPaid;

/**
 * Parses input arguments and creates a new SetInstallmentCommand object
 */
public class SetInstallmentCommandParser implements Parser<SetInstallmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetInstallmentCommand
     * and returns an SetInstallmentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SetInstallmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_MONEY);
        if (!arePrefixesPresent(argMultimap, PREFIX_DESCRIPTION, PREFIX_MONEY)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    SetInstallmentCommand.MESSAGE_USAGE));
        }

        InstallmentDescription description = FinanceParserUtil
                .parseInstallmentDescription(argMultimap
                        .getValue(PREFIX_DESCRIPTION)
                        .get());
        InstallmentMoneyPaid subscriptionFee = FinanceParserUtil
                .parseInstallmentMoneySpent(argMultimap
                        .getValue(PREFIX_MONEY)
                        .get());

        Installment installment = new Installment(description, subscriptionFee);

        return new SetInstallmentCommand(installment);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
