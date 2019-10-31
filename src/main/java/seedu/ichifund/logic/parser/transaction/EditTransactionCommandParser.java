package seedu.ichifund.logic.parser.transaction;

import static java.util.Objects.requireNonNull;
import static seedu.ichifund.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DAY;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_MONTH;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_TRANSACTION_TYPE;
import static seedu.ichifund.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.ichifund.commons.core.index.Index;
import seedu.ichifund.logic.commands.transaction.EditTransactionCommand;
import seedu.ichifund.logic.commands.transaction.EditTransactionCommand.EditTransactionDescriptor;
import seedu.ichifund.logic.parser.ArgumentMultimap;
import seedu.ichifund.logic.parser.ArgumentTokenizer;
import seedu.ichifund.logic.parser.Parser;
import seedu.ichifund.logic.parser.ParserUtil;
import seedu.ichifund.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditTransactionCommand object
 */
public class EditTransactionCommandParser implements Parser<EditTransactionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditTransactionCommand
     * and returns an EditTransactionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditTransactionCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_AMOUNT, PREFIX_CATEGORY, PREFIX_DAY,
                        PREFIX_MONTH, PREFIX_YEAR, PREFIX_TRANSACTION_TYPE);
        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditTransactionCommand.MESSAGE_USAGE), pe);
        }

        EditTransactionDescriptor editTransactionDescriptor = new EditTransactionDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editTransactionDescriptor.setDescription(ParserUtil
                    .parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editTransactionDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editTransactionDescriptor.setCategory(ParserUtil
                    .parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get()));
        }
        if (argMultimap.getValue(PREFIX_DAY).isPresent()) {
            editTransactionDescriptor.setDay(ParserUtil.parseDay(argMultimap.getValue(PREFIX_DAY).get()));
        }
        if (argMultimap.getValue(PREFIX_MONTH).isPresent()) {
            editTransactionDescriptor.setMonth(ParserUtil.parseMonth(argMultimap.getValue(PREFIX_MONTH).get()));
        }
        if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            editTransactionDescriptor.setYear(ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get()));
        }
        if (argMultimap.getValue(PREFIX_TRANSACTION_TYPE).isPresent()) {
            editTransactionDescriptor.setTransactionType(ParserUtil
                    .parseTransactionType(argMultimap.getValue(PREFIX_TRANSACTION_TYPE).get()));
        }

        if (!editTransactionDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditTransactionCommand.MESSAGE_NOT_EDITED);
        }

        return new EditTransactionCommand(index, editTransactionDescriptor);
    }
}
