package seedu.ichifund.logic.parser.repeater;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.commons.util.CollectionUtil.isAnyNonNull;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_END_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH_END_OFFSET;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH_START_OFFSET;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_START_YEAR;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;

import java.util.Optional;

import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.Command;
import seedu.ichifund.logic.commands.CommandResult;
import seedu.ichifund.logic.commands.exceptions.CommandException;
import seedu.ichifund.logic.commands.repeater.EditRepeaterCommand;
import seedu.ichifund.logic.commands.repeater.EditRepeaterCommand.EditRepeaterDescriptor;
import seedu.ichifund.logic.parser.ArgumentMultimap;
import seedu.ichifund.logic.parser.ArgumentTokenizer;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.Prefix;
import seedu.ichifund.logic.parser.exceptions.ParseException;
import seedu.ichifund.model.Description;
import seedu.ichifund.model.Model;
import seedu.ichifund.model.amount.Amount;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.repeater.MonthOffset;
import seedu.ichifund.model.transaction.Category;
import seedu.ichifund.model.transaction.TransactionType;

/**
 * Parses input arguments and creates a new EditRepeaterCommand object
 */
public class EditRepeaterCommandParser implements Parser<EditRepeaterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditRepeaterCommand
     * and returns an EditRepeaterCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRepeaterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_DESCRIPTION, PREFIX_AMOUNT, PREFIX_CATEGORY, PREFIX_TRANSACTION_TYPE,
                PREFIX_MONTH_START_OFFSET, PREFIX_MONTH_END_OFFSET, PREFIX_START_MONTH, PREFIX_START_YEAR,
                PREFIX_END_MONTH, PREFIX_END_YEAR);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditRepeaterCommand.MESSAGE_USAGE),
                    pe);
        }

        EditRepeaterDescriptor editRepeaterDescriptor = new EditRepeaterDescriptor();

        if (!editRepeaterDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRepeaterCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRepeaterCommand(index, editRepeaterDescriptor);
    }

}
