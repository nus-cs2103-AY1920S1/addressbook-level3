package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import budgetbuddy.logic.commands.loancommands.LoanCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.Prefix;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * Parses input arguments and creates a new LoanCommand object.
 */
public class LoanCommandParser implements CommandParser<LoanCommand> {
    @Override
    public String name() {
        return LoanCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the LoanCommand
     * and returns an LoanCommand object for execution.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public LoanCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE);

        String directionString = argMultimap.getPreamble().toUpperCase();
        if (!(directionString.equals(Direction.IN.toString()) || directionString.equals(Direction.OUT.toString()))) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON, PREFIX_AMOUNT)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanCommand.MESSAGE_USAGE));
        }

        if (argMultimap.getValueCount(PREFIX_PERSON) > 1
                || argMultimap.getValueCount(PREFIX_AMOUNT) > 1
                || argMultimap.getValueCount(PREFIX_DESCRIPTION) > 1
                || argMultimap.getValueCount(PREFIX_DATE) > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanCommand.MESSAGE_USAGE));
        }

        Direction direction = Direction.valueOf(directionString.toUpperCase());
        Person person = new Person(CommandParserUtil.parseName(argMultimap.getValue(PREFIX_PERSON).get()));
        Amount amount = CommandParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        Optional<String> optionalDescription = argMultimap.getValue(PREFIX_DESCRIPTION);
        Description description = optionalDescription.isPresent()
                ? CommandParserUtil.parseDescription(optionalDescription.get())
                : new Description("");

        Optional<String> optionalDate = argMultimap.getValue(PREFIX_DATE);
        Date date = new Date();
        if (optionalDate.isPresent()) {
            date = CommandParserUtil.parseDate(optionalDate.get());
        }

        Status status = Status.UNPAID;

        Loan loan = new Loan(person, direction, amount, date, description, status);

        return new LoanCommand(loan);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
