package dukecooks.logic.parser.health;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DATETIME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_TYPE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_VALUE;
import static java.util.Objects.requireNonNull;

import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.health.EditRecordCommand;
import dukecooks.logic.commands.health.EditRecordCommand.EditRecordDescriptor;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditRecordCommand object
 */
public class EditRecordCommandParser implements Parser<EditRecordCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditRecordCommand
     * and returns an EditRecordCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditRecordCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_VALUE, PREFIX_DATETIME, PREFIX_TYPE);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRecordCommand.MESSAGE_USAGE), pe);
        }

        EditRecordDescriptor editRecordDescriptor = new EditRecordDescriptor();
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            editRecordDescriptor.setType(ParserUtil.parseType(argMultimap.getValue(PREFIX_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editRecordDescriptor.setTimestamp(ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_DATETIME).get()));
        }
        if (argMultimap.getValue(PREFIX_VALUE).isPresent()) {
            editRecordDescriptor.setValue(ParserUtil.parseValue(argMultimap.getValue(PREFIX_VALUE).get()));
        }

        if (!editRecordDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRecordCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRecordCommand(index, editRecordDescriptor);
    }

}
