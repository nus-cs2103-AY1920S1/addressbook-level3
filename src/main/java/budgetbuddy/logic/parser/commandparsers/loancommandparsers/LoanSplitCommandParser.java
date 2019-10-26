package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_USER;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.commands.loancommands.LoanSplitCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.attributes.Description;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * Parses input arguments and creates a new LoanSplitCommand object.
 */
public class LoanSplitCommandParser implements CommandParser<LoanSplitCommand> {
    @Override
    public String name() {
        return LoanSplitCommand.COMMAND_WORD;
    }

    @Override
    public LoanSplitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(
                args, PREFIX_USER, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_PERSON, PREFIX_AMOUNT);

        if (argMultiMap.getAllValues(PREFIX_USER).size() > 1
                || argMultiMap.getAllValues(PREFIX_DESCRIPTION).size() > 1
                || argMultiMap.getAllValues(PREFIX_DATE).size() > 1
                || argMultiMap.getAllValues(PREFIX_PERSON).size() < 1
                || argMultiMap.getAllValues(PREFIX_AMOUNT).size() < 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanSplitCommand.MESSAGE_USAGE));
        }

        Optional<String> optionalUserArg = argMultiMap.getValue(PREFIX_USER);
        Optional<Person> optionalUser = optionalUserArg.isPresent()
                ? Optional.of(new Person(CommandParserUtil.parseName(optionalUserArg.get())))
                : Optional.empty();

        Optional<String> optionalDescriptionArg = argMultiMap.getValue(PREFIX_DESCRIPTION);
        Optional<Description> optionalDescription = optionalDescriptionArg.isPresent()
                ? Optional.of(CommandParserUtil.parseDescription(optionalDescriptionArg.get()))
                : Optional.empty();

        Optional<String> optionalDateArg = argMultiMap.getValue(PREFIX_DATE);
        Optional<Date> optionalDate = optionalDateArg.isPresent()
                ? Optional.of(CommandParserUtil.parseDate(optionalDateArg.get()))
                : Optional.empty();

        if ((optionalUser.isEmpty() && optionalDescription.isPresent())
            || (optionalUser.isEmpty() && optionalDate.isPresent())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanSplitCommand.MESSAGE_USAGE));
        }

        List<Person> persons = new ArrayList<Person>();
        List<Amount> amounts = new ArrayList<Amount>();

        for (String personName : argMultiMap.getAllValues(PREFIX_PERSON)) {
            persons.add(new Person(CommandParserUtil.parseName(personName)));
        }

        for (String amountStr : argMultiMap.getAllValues(PREFIX_AMOUNT)) {
            amounts.add(CommandParserUtil.parseAmount(amountStr));
        }

        try {
            return new LoanSplitCommand(persons, amounts, optionalUser, optionalDescription, optionalDate);
        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }
    }
}
