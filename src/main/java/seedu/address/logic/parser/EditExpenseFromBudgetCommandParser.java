package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.budget.EditExpenseFromBudgetCommand;
import seedu.address.logic.commands.expense.EditExpenseCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Dummy.
 */
public class EditExpenseFromBudgetCommandParser {
    /**
     * Parses the given {@code String} of arguments in the context of the EditExpenseFromBudgetCommand
     * and returns an EditExpenseFromBudgetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditExpenseFromBudgetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESCRIPTION, PREFIX_PRICE, PREFIX_CATEGORY, PREFIX_TIMESTAMP);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditExpenseFromBudgetCommand.MESSAGE_USAGE), pe);
        }

        EditExpenseCommand.EditExpenseDescriptor editExpenseDescriptor = new EditExpenseCommand.EditExpenseDescriptor();
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
            throw new ParseException(EditExpenseFromBudgetCommand.MESSAGE_NOT_EDITED);
        }

        return new EditExpenseFromBudgetCommand(index, editExpenseDescriptor);
    }
}
