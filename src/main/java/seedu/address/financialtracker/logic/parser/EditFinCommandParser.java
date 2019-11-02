package seedu.address.financialtracker.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.financialtracker.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.financialtracker.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.financialtracker.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.financialtracker.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.financialtracker.logic.parser.CliSyntax.PREFIX_TYPE;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.financialtracker.logic.commands.EditFinCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditFinCommand object
 */
public class EditFinCommandParser implements Parser<EditFinCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditFinCommand
     * and returns an EditFinCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditFinCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DESCRIPTION, PREFIX_TYPE,
                        PREFIX_DATE, PREFIX_TIME);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    EditFinCommand.MESSAGE_USAGE), pe);
        }

        EditFinCommand.EditExpenseDescriptor editExpenseDescriptor = new EditFinCommand.EditExpenseDescriptor();
        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editExpenseDescriptor.setAmount(ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()));
        }
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            editExpenseDescriptor.setDescription(ParserUtil
                    .parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get()));
        }
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            editExpenseDescriptor.setType(ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get()));
        }

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editExpenseDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }

        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editExpenseDescriptor.setTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }

        if (!editExpenseDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditFinCommand.MESSAGE_NOT_EDITED);
        }

        return new EditFinCommand(index, editExpenseDescriptor);
    }
}
