package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPENSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PARTICIPANT;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.SettleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.activity.Amount;

/**
 * Parses input arguments and creates a new ExpenseCommand object
 */
public class SettleCommandParser implements Parser<SettleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ExpenseCommand
     * and returns an ExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SettleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PARTICIPANT, PREFIX_EXPENSE);
        if (!arePrefixesPresent(argMultimap, PREFIX_PARTICIPANT)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SettleCommand.MESSAGE_USAGE));
        }

        List<String> persons = argMultimap.getAllValues(PREFIX_PARTICIPANT);
        List<String> amounts = argMultimap.getAllValues(PREFIX_EXPENSE);

        if (persons.size() != 2) {
            throw new ParseException(SettleCommand.MESSAGE_NOT_TWO_PEOPLE);
        }

        if (persons.get(0).equals(persons.get(1))) {
            throw new ParseException(SettleCommand.MESSAGE_REPEATED_PERSON);
        }

        if (amounts.size() > 1) {
            throw new ParseException(SettleCommand.MESSAGE_TOO_MANY_AMOUNTS);
        }

        if (amounts.size() == 0) {
            return new SettleCommand(persons, new Amount(0));
        } else {
            return new SettleCommand(persons, ParserUtil.parseAmount(amounts.get(0)));
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
