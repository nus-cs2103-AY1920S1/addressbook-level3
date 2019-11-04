package seedu.moolah.logic.parser.expense;

import static java.util.Objects.requireNonNull;
import static seedu.moolah.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.moolah.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.Collections;
import java.util.List;

import seedu.moolah.commons.core.index.Index;
import seedu.moolah.logic.commands.expense.EditExpenseCommand;
import seedu.moolah.logic.commands.expense.EditExpenseCommand.EditExpenseDescriptor;
import seedu.moolah.logic.parser.ArgumentMultimap;
import seedu.moolah.logic.parser.ArgumentTokenizer;
import seedu.moolah.logic.parser.Parser;
import seedu.moolah.logic.parser.ParserUtil;
import seedu.moolah.logic.parser.Prefix;
import seedu.moolah.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object
 */
public class EditExpenseCommandParser implements Parser<EditExpenseCommand> {

    public static final List<Prefix> REQUIRED_PREFIXES = Collections.unmodifiableList(List.of());
    public static final List<Prefix> OPTIONAL_PREFIXES = Collections.unmodifiableList(List.of(
            PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_CATEGORY, PREFIX_TIMESTAMP
    ));

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditExpenseCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_CATEGORY, PREFIX_TIMESTAMP);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditExpenseCommand.MESSAGE_USAGE), pe);
        }

        EditExpenseDescriptor editExpenseDescriptor = new EditExpenseDescriptor();
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editExpenseDescriptor.setDescription(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_PRICE).isPresent()) {
            editExpenseDescriptor.setPrice(
                    ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get()));
        }

        if (argMultimap.getValue(PREFIX_CATEGORY).isPresent()) {
            editExpenseDescriptor.setCategory(
                    ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get()));
        }

        if (argMultimap.getValue(PREFIX_TIMESTAMP).isPresent()) {
            editExpenseDescriptor.setTimestamp(
                    ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_TIMESTAMP).get()));
        }

        if (!editExpenseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditExpenseCommand.MESSAGE_NOT_EDITED);
        }

        return new EditExpenseCommand(index, editExpenseDescriptor);
    }
}
