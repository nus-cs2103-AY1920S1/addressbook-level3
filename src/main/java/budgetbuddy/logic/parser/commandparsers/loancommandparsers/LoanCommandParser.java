package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;

import java.time.LocalDate;
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

        checkArgumentMultimap(argMultimap);

        Direction direction = parseDirection(argMultimap.getPreamble().toUpperCase());
        Person person = parsePerson(argMultimap.getValue(PREFIX_PERSON).get());
        Amount amount = parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        LocalDate date = parseDate(argMultimap.getValue(PREFIX_DATE));
        Description description = parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION));

        Loan toAdd = new Loan(person, direction, amount, date, description, Status.UNPAID);
        return new LoanCommand(toAdd);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Checks if the given {@code ArgumentMultimap} is valid for parsing into a {@code LoanCommand}.
     * @throws ParseException If the {@code argMultimap} is invalid.
     */
    private void checkArgumentMultimap(ArgumentMultimap argMultimap) throws ParseException {
        String directionString = argMultimap.getPreamble().toUpperCase();
        if (!(directionString.equals(Direction.IN.toString())
                || directionString.equals(Direction.OUT.toString()))) {
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
    }

    /**
     * Parses the given string into a {@code Person} object.
     * @return The parsed {@code Person}.
     * @throws ParseException If the string cannot be parsed into a {@code Person}.
     */
    private Person parsePerson(String personName) throws ParseException {
        return new Person(CommandParserUtil.parseName(personName));
    }

    /**
     * Parses the given string into a {@code Direction}.
     * @return The parsed {@code Direction}.
     * @throws ParseException If the string cannot be parsed into a {@code Direction}.
     */
    private Direction parseDirection(String directionString) throws ParseException {
        return Direction.valueOf(directionString);
    }

    /**
     * Parses the given string into a {@code Amount} object.
     * @return The parsed {@code Amount}.
     * @throws ParseException If the string cannot be parsed into a {@code Amount},
     * or if the parsed {@code Amount} has a value of zero dollars.
     */
    private Amount parseAmount(String amountStr) throws ParseException {
        Amount amount = CommandParserUtil.parseAmount(amountStr);
        if (amount.toLong() == 0) {
            throw new ParseException(Loan.MESSAGE_AMOUNT_POSITIVE_CONSTRAINT);
        }
        return amount;
    }

    /**
     * Parses the given {@code Optional} string into a {@code Description} object.
     * If the {@code Optional} is empty, a blank {@code Description} is returned.
     * @return The parsed {@code Description}.
     * @throws ParseException If the string cannot be parsed into a {@code Description}.
     */
    private Description parseDescription(Optional<String> optionalDescription) throws ParseException {
        return optionalDescription.isPresent()
                ? CommandParserUtil.parseDescription(optionalDescription.get())
                : new Description("");
    }

    /**
     * Parses the given {@code Optional} string into a {@code LocalDate} object.
     * If the {@code Optional} is empty, the current {@code LocalDate} is returned.
     * @return The parsed {@code LocalDate}.
     * @throws ParseException If the string cannot be parsed into a {@code LocalDate}.
     */
    private LocalDate parseDate(Optional<String> optionalDate) throws ParseException {
        return optionalDate.isPresent() ? CommandParserUtil.parseDate(optionalDate.get()) : LocalDate.now();
    }
}
