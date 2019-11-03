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
import budgetbuddy.model.attributes.Name;
import budgetbuddy.model.person.Person;
import budgetbuddy.model.transaction.Amount;

/**
 * Parses input arguments and creates a new LoanSplitCommand object.
 */
public class LoanSplitCommandParser implements CommandParser<LoanSplitCommand> {

    public final String MESSAGE_DUPLICATE_PERSONS = "Duplicate persons found in the list of persons. "
            + "Takes note that the list is case insensitive.";

    @Override
    public String name() {
        return LoanSplitCommand.COMMAND_WORD;
    }

    @Override
    public LoanSplitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(
                args, PREFIX_USER, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_PERSON, PREFIX_AMOUNT);

        if (argMultiMap.getValueCount(PREFIX_USER) > 1
                || argMultiMap.getValueCount(PREFIX_DESCRIPTION) > 1
                || argMultiMap.getValueCount(PREFIX_DATE) > 1
                || argMultiMap.getValueCount(PREFIX_PERSON) < 1
                || argMultiMap.getValueCount(PREFIX_AMOUNT) < 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanSplitCommand.MESSAGE_USAGE));
        }

        // parse optional user input for auto-adding calculated results to loan list

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

        // parse lists of persons and amounts

        List<Person> persons = new ArrayList<Person>();
        List<Amount> amounts = new ArrayList<Amount>();

        List<String> personNames = argMultiMap.getAllValues(PREFIX_PERSON);
        for (int i = 0; i < personNames.size(); i++) {
            String currPersonName = personNames.get(i);
            // check for persons with the same name (case-insensitive)
            for (int j = i + 1; j < personNames.size(); j++) {
                if (currPersonName.equalsIgnoreCase(personNames.get(j))) {
                    throw new ParseException(MESSAGE_DUPLICATE_PERSONS);
                }
            }
            persons.add(new Person(CommandParserUtil.parseName(currPersonName)));
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
