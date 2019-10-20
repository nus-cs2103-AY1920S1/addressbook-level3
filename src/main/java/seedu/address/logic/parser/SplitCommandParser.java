package seedu.address.logic.parser;

import seedu.address.logic.commands.InCommand;
import seedu.address.logic.commands.SplitCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Date;
import seedu.address.model.person.Name;
import seedu.address.model.transaction.Amount;
import seedu.address.model.transaction.SplitTransaction;
import seedu.address.model.transaction.Transaction;

import java.util.Set;
import java.util.stream.Stream;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.*;

public class SplitCommandParser implements Parser<SplitCommand> {

    @Override
    public SplitCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_AMOUNT);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_NAME, PREFIX_AMOUNT)
                || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SplitCommand.MESSAGE_USAGE));
        }

        Set<Name> nameList = ParserUtil.parseNames(argumentMultimap.getAllValues(PREFIX_NAME));
        Amount amount = ParserUtil.parseAmount(argumentMultimap.getValue(PREFIX_AMOUNT).get());
        Date date = ParserUtil.parseDate(argumentMultimap.getValue(PREFIX_DATE).get());


        Transaction transaction = new SplitTransaction(amount, date, nameList.size());

        return new SplitCommand(transaction);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
