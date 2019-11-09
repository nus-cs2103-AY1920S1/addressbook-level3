/*
package seedu.ichifund.logic.parser.loan;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_YEAR;

import java.util.stream.Stream;
import java.util.Optional;

import static seedu.ichifund.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.loan.EditLoanCommand;
import seedu.ichifund.logic.parser.FeatureParser;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.logic.parser.ArgumentMultimap;
import seedu.ichifund.logic.parser.ArgumentTokenizer;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.Prefix;
import seedu.ichifund.logic.parser.exceptions.ParseException;

import seedu.ichifund.model.Description;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;
import seedu.ichifund.model.loan.Loan;
import seedu.ichifund.model.loan.LoanId;
import seedu.ichifund.model.loan.Name;


public class EditLoanCommandParser extends Command{
    public static final String COMMAND_WORD = "edit";

    public EditLoanCommandParser parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DESCRIPTION, PREFIX_AMOUNT,
                        PREFIX_START_DAY, PREFIX_START_MONTH, PREFIX_START_YEAR,
                        PREFIX_END_MONTH, PREFIX_END_YEAR, PREFIX_END_DAY);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRepeaterCommand.MESSAGE_USAGE),
                    pe);
        }
    }
}
*/
