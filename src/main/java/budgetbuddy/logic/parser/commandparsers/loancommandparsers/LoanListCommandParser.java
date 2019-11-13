package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.commons.util.CollectionUtil.hasDuplicates;
import static budgetbuddy.logic.parser.CliSyntax.KEYWORD_LOAN_IN;
import static budgetbuddy.logic.parser.CliSyntax.KEYWORD_LOAN_OUT;
import static budgetbuddy.logic.parser.CliSyntax.KEYWORD_LOAN_PAID;
import static budgetbuddy.logic.parser.CliSyntax.KEYWORD_LOAN_UNPAID;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DATE;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_PERSON;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_SORT;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ARG_AMOUNT;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ARG_DATE;
import static budgetbuddy.logic.parser.CliSyntax.SORT_ARG_PERSON;
import static budgetbuddy.model.loan.LoanFilters.getDirectionPredicate;
import static budgetbuddy.model.loan.LoanFilters.getStatusPredicate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import budgetbuddy.logic.commands.loancommands.LoanListCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.CommandParserUtil;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.attributes.Direction;
import budgetbuddy.model.loan.Loan;
import budgetbuddy.model.loan.LoanSorters;
import budgetbuddy.model.loan.Status;
import budgetbuddy.model.loan.predicates.AmountMatchPredicate;
import budgetbuddy.model.loan.predicates.DateMatchPredicate;
import budgetbuddy.model.loan.predicates.DescriptionMatchPredicate;
import budgetbuddy.model.loan.predicates.PersonMatchPredicate;
import budgetbuddy.model.person.Person;

/**
 * Parses the <code>list</code> command.
 */
public class LoanListCommandParser implements CommandParser<LoanListCommand> {

    public static final String MESSAGE_DUPLICATE_FILTERS = "Each filter can only be used once.";

    private static final Pattern DIRECTION_STATUS_PATTERN = Pattern.compile("(out|in|paid|unpaid)");

    @Override
    public String name() {
        return LoanListCommand.COMMAND_WORD;
    }

    @Override
    public LoanListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_SORT, PREFIX_PERSON, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_DESCRIPTION);

        if (argMultimap.getAllValues(PREFIX_SORT).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanListCommand.MESSAGE_USAGE));
        }

        // parse sort arguments
        Optional<Comparator<Loan>> optionalSorter = argMultimap.getValue(PREFIX_SORT).isPresent()
                ? parseSortArg(argMultimap.getValue(PREFIX_SORT))
                : Optional.empty();

        List<Predicate<Loan>> filters = new ArrayList<Predicate<Loan>>();

        // parse direction and status filters
        if (!argMultimap.getPreamble().isBlank()) {
            String[] preambleArr = argMultimap.getPreamble().split("\\s+");
            if (preambleArr.length <= 2) {
                filters.addAll(parseDirectionStatusFilters(preambleArr));
            } else {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanListCommand.MESSAGE_USAGE));
            }
        }

        // parse person, amount, date, and description filters
        filters.addAll(parseFiltersWithPrefixes(argMultimap));

        return new LoanListCommand(optionalSorter, filters);
    }

    /**
     * Parses an optional sort argument into a comparator for loans.
     * @return The parsed comparator.
     * @throws ParseException If the given argument does not correspond to a comparator in {@code LoanSorters}.
     */
    private Optional<Comparator<Loan>> parseSortArg(Optional<String> optionalSortArg) throws ParseException {
        if (optionalSortArg.isEmpty()) {
            return Optional.empty();
        }

        switch (optionalSortArg.get()) {
        case SORT_ARG_AMOUNT:
            return Optional.of(LoanSorters.AMOUNT_ASC);
        case SORT_ARG_PERSON:
            return Optional.of(LoanSorters.PERSON);
        case SORT_ARG_DATE:
            return Optional.of(LoanSorters.DATE_NEWEST);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanListCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Parses the preamble into predicates for filtering loans by direction and/or status.
     * @param preambleArr The preamble as a string array. Each element is one word.
     * @return The list of parsed predicates.
     * @throws ParseException If there are duplicate filters or
     * if the argument does not match any available direction or status filters.
     */
    private List<Predicate<Loan>> parseDirectionStatusFilters(String[] preambleArr) throws ParseException {
        if (hasDuplicates(Arrays.asList(preambleArr))) {
            throw new ParseException(MESSAGE_DUPLICATE_FILTERS);
        }

        List<Predicate<Loan>> filters = new ArrayList<Predicate<Loan>>();

        Matcher directionStatusMatcher;
        for (String filterStr : preambleArr) {
            directionStatusMatcher = DIRECTION_STATUS_PATTERN.matcher(filterStr);
            if (!directionStatusMatcher.matches()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanListCommand.MESSAGE_USAGE));
            }

            switch (directionStatusMatcher.group()) {
            case KEYWORD_LOAN_IN:
                filters.add(getDirectionPredicate(Direction.IN));
                break;
            case KEYWORD_LOAN_OUT:
                filters.add(getDirectionPredicate(Direction.OUT));
                break;
            case KEYWORD_LOAN_PAID:
                filters.add(getStatusPredicate(Status.PAID));
                break;
            case KEYWORD_LOAN_UNPAID:
                filters.add(getStatusPredicate(Status.UNPAID));
                break;
            default:
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanListCommand.MESSAGE_USAGE));
            }
        }

        return filters;
    }

    /**
     * Parses the filter arguments with prefixes into predicates for filtering loans.
     * @return The list of parsed predicates.
     * @throws ParseException If there are duplicate filters present, or if an error occurs while parsing.
     */
    private List<Predicate<Loan>> parseFiltersWithPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        // parse person, amount, date and description filters
        if (hasDuplicates(argMultimap.getAllValues(PREFIX_PERSON))
                || hasDuplicates(argMultimap.getAllValues(PREFIX_AMOUNT))
                || hasDuplicates(argMultimap.getAllValues(PREFIX_DATE))
                || hasDuplicates(argMultimap.getAllValues(PREFIX_DESCRIPTION))) {
            throw new ParseException(MESSAGE_DUPLICATE_FILTERS);
        }

        List<Predicate<Loan>> filters = new ArrayList<Predicate<Loan>>();
        for (String personStr : argMultimap.getAllValues(PREFIX_PERSON)) {
            filters.add(new PersonMatchPredicate(new Person(CommandParserUtil.parseName(personStr))));
        }
        for (String amountStr : argMultimap.getAllValues(PREFIX_AMOUNT)) {
            filters.add(new AmountMatchPredicate(CommandParserUtil.parseAmount(amountStr)));
        }
        for (String dateStr : argMultimap.getAllValues(PREFIX_DATE)) {
            filters.add(new DateMatchPredicate(CommandParserUtil.parseDate(dateStr)));
        }
        for (String descriptionStr : argMultimap.getAllValues(PREFIX_DESCRIPTION)) {
            filters.add(new DescriptionMatchPredicate(CommandParserUtil.parseDescription(descriptionStr)));
        }

        return filters;
    }
}
