package budgetbuddy.logic.parser.commandparsers.loancommandparsers;

import static budgetbuddy.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static budgetbuddy.logic.parser.CliSyntax.PREFIX_SORT;

import java.util.Optional;

import budgetbuddy.logic.commands.loancommands.LoanListCommand;
import budgetbuddy.logic.parser.ArgumentMultimap;
import budgetbuddy.logic.parser.ArgumentTokenizer;
import budgetbuddy.logic.parser.CommandParser;
import budgetbuddy.logic.parser.exceptions.ParseException;
import budgetbuddy.model.LoansManager.SortBy;

/**
 * Parses the <code>list</code> command.
 */
public class LoanListCommandParser implements CommandParser<LoanListCommand> {

    public static final String SORT_ARG_DATE = "w";
    public static final String SORT_ARG_PERSON = "p";
    public static final String SORT_ARG_AMOUNT = "x";

    @Override
    public String name() {
        return LoanListCommand.COMMAND_WORD;
    }

    @Override
    public LoanListCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SORT);

        if (argMultimap.getAllValues(PREFIX_SORT).size() > 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanListCommand.MESSAGE_USAGE));
        }

        Optional<String> optionalSortArg = argMultimap.getValue(PREFIX_SORT);
        Optional<SortBy> optionalSortBy = parseSortArg(optionalSortArg);

        return new LoanListCommand(optionalSortBy);
    }

    private Optional<SortBy> parseSortArg(Optional<String> optionalSortArg) throws ParseException {
        if (optionalSortArg.isEmpty()) {
            return Optional.empty();
        }

        switch (optionalSortArg.get()) {
        case SORT_ARG_AMOUNT:
            return Optional.of(SortBy.AMOUNT);
        case SORT_ARG_PERSON:
            return Optional.of(SortBy.PERSON);
        case SORT_ARG_DATE:
            return Optional.of(SortBy.DATE);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoanListCommand.MESSAGE_USAGE));
        }
    }
}
