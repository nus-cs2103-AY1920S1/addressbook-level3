package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;

import java.util.Optional;
import java.util.stream.Stream;

import budgetbuddy.logic.commands.loancommands.AddLoanCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.Prefix;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.Direction;
import budgetbuddy.model.loan.Description;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.LoanList;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.loan.stub.Date;
import budgetbuddy.model.person.Name;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

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

        Name name = CommandParserUtil.parseName(argMultimap.getValue(PREFIX_PERSON).get());
        Person person = new Person(name, new LoanList());

        Direction direction = Direction.valueOf(directionString.toUpperCase());
        Amount amount = CommandParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());

        Optional<String> optionalDescription = argMultimap.getValue(PREFIX_DESCRIPTION);
        Description description = optionalDescription.isPresent()
                ? CommandParserUtil.parseDescription(optionalDescription.get())
                : new Description("");

        Optional<String> optionalDate = argMultimap.getValue(PREFIX_DATE);
        Date date = optionalDate.isPresent()
                ? CommandParserUtil.parseDate(optionalDate.get())
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
