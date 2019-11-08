package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.commons.util.CollectionUtil.requireAllNonNull;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_MAX_SHARE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_USER;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import budgetbuddy.logic.commands.exceptions.CommandException;
import budgetbuddy.logic.commands.loancommands.loansplitcommand.LoanSplitCommand;
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

    public static final String MESSAGE_DUPLICATE_PERSONS = "Duplicate persons found in the list of persons. "
            + "Takes note that the list is case insensitive.";
    public static final String MESSAGE_SHARES_MORE_THAN_PERSONS =
            "The number of limits cannot exceed the number of persons.";
    public static final String MESSAGE_MAX_SHARE_CONSTRAINTS =
            "A person's limit must be a non-negative number.";

    private List<Person> persons;
    private List<Amount> amounts;
    private List<Long> maxShares;

    private Optional<Person> optionalUser;
    private Optional<Description> optionalDescription;
    private Optional<LocalDate> optionalDate;

    @Override
    public String name() {
        return LoanSplitCommand.COMMAND_WORD;
    }

    @Override
    public LoanSplitCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_USER, PREFIX_DESCRIPTION, PREFIX_DATE, PREFIX_PERSON, PREFIX_AMOUNT, PREFIX_MAX_SHARE);

        if (argMultimap.getValueCount(PREFIX_USER) > 1
                || argMultimap.getValueCount(PREFIX_DESCRIPTION) > 1
                || argMultimap.getValueCount(PREFIX_DATE) > 1
                || argMultimap.getValueCount(PREFIX_PERSON) < 1
                || argMultimap.getValueCount(PREFIX_AMOUNT) < 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanSplitCommand.MESSAGE_USAGE));
        }

        parsePersonsAmounts(argMultimap);
        parseMaxShares(argMultimap);
        parseOptionalLoanAddArgs(argMultimap);

        try {
            requireAllNonNull(persons, amounts, maxShares, optionalUser, optionalDescription, optionalDate);
            return new LoanSplitCommand(persons, amounts, maxShares, optionalUser, optionalDescription, optionalDate);
        } catch (CommandException e) {
            throw new ParseException(e.getMessage());
        }
    }

    /**
     * Parses the persons and amounts entered into two lists.
     * @throws ParseException If a persons with duplicate names (case-insensitive) is detected.
     */
    private void parsePersonsAmounts(ArgumentMultimap argMultimap) throws ParseException {
        persons = new ArrayList<Person>();
        amounts = new ArrayList<Amount>();

        List<String> personNames = argMultimap.getAllValues(PREFIX_PERSON);
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

        for (String amountStr : argMultimap.getAllValues(PREFIX_AMOUNT)) {
            amounts.add(CommandParserUtil.parseAmount(amountStr));
        }
    }

    /**
     * Parses the max shares entered into a list of {@code long} values.
     * @throws ParseException If a max share entered is non-negative.
     */
    private void parseMaxShares(ArgumentMultimap argMultimap) throws ParseException {
        maxShares = new ArrayList<Long>();

        if (argMultimap.getValueCount(PREFIX_MAX_SHARE) > persons.size()) {
            throw new ParseException(MESSAGE_SHARES_MORE_THAN_PERSONS);
        }

        for (String maxShareStr : argMultimap.getAllValues(PREFIX_MAX_SHARE)) {
            if (maxShareStr.length() >= Amount.MAX_AMOUNT.length()) {
                throw new ParseException(Amount.MESSAGE_CONSTRAINTS);
            }
            maxShareStr += "00"; // calculation to be done in cents
            long maxShare = Long.parseLong(maxShareStr);
            if (maxShare < -1) {
                throw new ParseException(MESSAGE_MAX_SHARE_CONSTRAINTS);
            }
            maxShares.add(maxShare);
        }
    }

    /**
     * Parses the optional arguments for auto-adding loans into {@code Optional} objects.
     * @throws ParseException If an error occurs during the parsing of the user's name, given date, or description.
     */
    private void parseOptionalLoanAddArgs(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> optionalUserArg = argMultimap.getValue(PREFIX_USER);
        optionalUser = optionalUserArg.isPresent()
                ? Optional.of(new Person(CommandParserUtil.parseName(optionalUserArg.get())))
                : Optional.empty();

        Optional<String> optionalDescriptionArg = argMultimap.getValue(PREFIX_DESCRIPTION);
        optionalDescription = optionalDescriptionArg.isPresent()
                ? Optional.of(CommandParserUtil.parseDescription(optionalDescriptionArg.get()))
                : Optional.empty();

        Optional<String> optionalDateArg = argMultimap.getValue(PREFIX_DATE);
        optionalDate = optionalDateArg.isPresent()
                ? Optional.of(CommandParserUtil.parseDate(optionalDateArg.get()))
                : Optional.empty();

        if ((optionalUser.isEmpty() && optionalDescription.isPresent())
                || (optionalUser.isEmpty() && optionalDate.isPresent())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanSplitCommand.MESSAGE_USAGE));
        }
    }
}
