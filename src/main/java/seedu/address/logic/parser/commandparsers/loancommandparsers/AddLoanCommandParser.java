package seedu.address.logic.parser.commandparsers.loancommandparsers;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.Optional;
import java.util.stream.Stream;

import seedu.address.logic.commands.loancommands.AddLoanCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Direction;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.loan.Description;
import seedu.address.model.person.loan.Loan;
import seedu.address.model.person.loan.LoanList;
import seedu.address.model.person.loan.Status;
import seedu.address.model.person.loan.stub.Date;
import seedu.address.model.transaction.Amount;

/**
 * Parses input arguments and creates a new AddLoanCommand object.
 */
public class AddLoanCommandParser implements CommandParser<AddLoanCommand> {
    @Override
    public String name() {
        return AddLoanCommand.COMMAND_WORD;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the AddLoanCommand
     * and returns an AddLoanCommand object for execution.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public AddLoanCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PERSON, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_DATE);

        String directionString = argMultimap.getPreamble().toUpperCase();
        if (!arePrefixesPresent(argMultimap, PREFIX_PERSON, PREFIX_AMOUNT)
                || !(directionString.equals(Direction.IN.toString())
                || directionString.equals(Direction.OUT.toString()))) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLoanCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_PERSON).get());
        Person person = new Person(name, new LoanList());

        Direction direction = Direction.valueOf(directionString.toUpperCase());
        Amount amount = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        Optional<String> optionalDescription = argMultimap.getValue(PREFIX_DESCRIPTION);
        Description description = optionalDescription.isPresent()
                ? ParserUtil.parseDescription(optionalDescription.get())
                : new Description("");

        Optional<String> optionalDate = argMultimap.getValue(PREFIX_DATE);
        Date date = optionalDate.isPresent()
                ? ParserUtil.parseDate(optionalDate.get())
                : new Date("20/12/2099");

        Status status = Status.UNPAID;

        Loan loan = new Loan(person, direction, amount, date, description, status);
        person.addLoan(loan);

        return new AddLoanCommand(loan);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
