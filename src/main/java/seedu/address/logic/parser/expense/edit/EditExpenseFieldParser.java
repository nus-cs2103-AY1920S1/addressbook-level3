package seedu.address.logic.parser.expense.edit;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BUDGET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DAY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.expense.edit.EditExpenseFieldCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.expense.ExpenseParserUtil;

/**
 * Parses commands related to editing expense fields.
 */
public class EditExpenseFieldParser implements Parser<EditExpenseFieldCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDayFieldCommand
     * and returns an EditDayFieldCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditExpenseFieldCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME,
                        PREFIX_BUDGET,
                        PREFIX_INDEX,
                        PREFIX_DAY_NUMBER);

        Optional<Index> index;

        try {
            index = Optional.ofNullable(ParserUtil.parseIndex(argMultimap.getPreamble()));
        } catch (ParseException pe) {
            index = Optional.empty();
            //throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            //        EditTripFieldCommand.MESSAGE_USAGE), pe);
        }

        if (!index.isEmpty()) {
            //edit by field specified by index only
            throw new UnsupportedOperationException("Parsing edit expense by index not yet supported.");

        }
        //edit by prefixes
        EditExpenseFieldCommand.EditExpenseDescriptor editExpenseDescriptor =
                new EditExpenseFieldCommand.EditExpenseDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editExpenseDescriptor.setName(ExpenseParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_BUDGET).isPresent()) {
            editExpenseDescriptor.setBudget(
                    ExpenseParserUtil.parseBudget(argMultimap.getValue(PREFIX_BUDGET).get()));
        }
        if (argMultimap.getValue(PREFIX_DAY_NUMBER).isPresent()) {
            editExpenseDescriptor.setDayNumber(ExpenseParserUtil.parseDayNumber(argMultimap
                    .getValue(PREFIX_DAY_NUMBER).get()));
        }
        if (!editExpenseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditExpenseFieldCommand.MESSAGE_NOT_EDITED);
        }

        return new EditExpenseFieldCommand(editExpenseDescriptor);
    }

}
