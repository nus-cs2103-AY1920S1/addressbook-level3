package dukecooks.logic.parser.health;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static dukecooks.logic.parser.CliSyntax.PREFIX_DATETIME;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REMARK;
import static dukecooks.logic.parser.CliSyntax.PREFIX_REMOVEREMARK;
import static dukecooks.logic.parser.CliSyntax.PREFIX_TYPE;
import static dukecooks.logic.parser.CliSyntax.PREFIX_VALUE;
import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import dukecooks.commons.core.Messages;
import dukecooks.commons.core.index.Index;
import dukecooks.logic.commands.health.EditRecordCommand;
import dukecooks.logic.commands.health.EditRecordCommand.EditRecordDescriptor;
import dukecooks.logic.parser.ArgumentMultimap;
import dukecooks.logic.parser.ArgumentTokenizer;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.ParserUtil;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.health.components.Remark;

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
                ArgumentTokenizer.tokenize(args, PREFIX_VALUE, PREFIX_DATETIME, PREFIX_TYPE,
                        PREFIX_REMARK, PREFIX_REMOVEREMARK);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditRecordCommand.MESSAGE_USAGE), pe);
        }

        EditRecordDescriptor editRecordDescriptor = new EditRecordDescriptor();
        if (argMultimap.getValue(PREFIX_TYPE).isPresent()) {
            throw new ParseException(Messages.MESSAGE_CANNOT_EDIT_RECORD_TYPE);
        }
        if (argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            editRecordDescriptor.setTimestamp(ParserUtil.parseTimestamp(argMultimap.getValue(PREFIX_DATETIME).get()));
        }
        if (argMultimap.getValue(PREFIX_VALUE).isPresent()) {
            editRecordDescriptor.setValue(ParserUtil.parseValue(argMultimap.getValue(PREFIX_VALUE).get()));
        }

        parseRemarksForEdit(argMultimap.getAllValues(PREFIX_REMARK))
                .ifPresent(editRecordDescriptor::addRemarks);

        parseRemarksForEdit(argMultimap.getAllValues(PREFIX_REMOVEREMARK))
                .ifPresent(editRecordDescriptor::removeRemarks);

        if (!editRecordDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditRecordCommand.MESSAGE_NOT_EDITED);
        }

        return new EditRecordCommand(index, editRecordDescriptor);
    }

    /**
     * Parses {@code Collection<String> remarks} into a {@code Set<Remark>} if {@code remarks}
     * is non-empty.
     * If {@code remarks} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Remark>} containing zero remarks.
     */
    private Optional<Set<Remark>> parseRemarksForEdit(Collection<String> remarks) throws ParseException {
        assert remarks != null;

        if (remarks.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> remarkSet = remarks.size() == 1 && remarks.contains("")
                ? Collections.emptySet() : remarks;
        return Optional.of(ParserUtil.parseRemarks(remarkSet));
    }

}
