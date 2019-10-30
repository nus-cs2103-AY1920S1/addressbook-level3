package seedu.address.logic.parser.remindercommandparsers;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TRACKER_TYPE;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.remindercommands.EditReminderCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditExpenseCommand object
 */
public class EditReminderCommandParser implements Parser<EditReminderCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditExpenseCommand
     * and returns an EditExpenseCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditReminderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DESC, PREFIX_INDEX, PREFIX_TRACKER_TYPE, PREFIX_AMOUNT);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format
                    (MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE), pe);
        }

        EditReminderCommand.EditReminderDescriptor editReminderDescriptor =
                new EditReminderCommand.EditReminderDescriptor();
        if (argMultimap.getValue(PREFIX_DESC).isPresent()) {
            editReminderDescriptor.setDesc(
                    ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get()));
        }

        if (argMultimap.getValue(PREFIX_AMOUNT).isPresent()) {
            editReminderDescriptor.setQuota(
                    (long) ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get()).value);
        }
        if (argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            List<Index> conditionIndices = ParserUtil.parseIndexes(argMultimap.getAllValues(PREFIX_INDEX));
        }

        if (!editReminderDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditReminderCommand.MESSAGE_NOT_EDITED);
        }

        return new EditReminderCommand(index, editReminderDescriptor);
    }
}
