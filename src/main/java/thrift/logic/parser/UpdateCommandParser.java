package thrift.logic.parser;

import static java.util.Objects.requireNonNull;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import thrift.commons.core.index.Index;
import thrift.logic.commands.UpdateCommand;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.tag.Tag;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_INDEX, CliSyntax.PREFIX_NAME, CliSyntax.PREFIX_COST,
                        CliSyntax.PREFIX_REMARK, CliSyntax.PREFIX_TAG);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreambleTillIndex());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE), pe);
        }

        UpdateCommand.UpdateTransactionDescriptor updateTransactionDescriptor =
                new UpdateCommand.UpdateTransactionDescriptor();

        if (argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()) {
            updateTransactionDescriptor.setDescription(ParserUtil.parseDescription(
                argMultimap.getValue(CliSyntax.PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_COST).isPresent()) {
            updateTransactionDescriptor.setValue(ParserUtil.parseValue(
                    argMultimap.getValue(CliSyntax.PREFIX_COST).get()));
        }

        if (argMultimap.getValue(CliSyntax.PREFIX_REMARK).isPresent()) {
            updateTransactionDescriptor.setRemark(ParserUtil.parseRemark(
                    argMultimap.getValue(CliSyntax.PREFIX_REMARK).get()
            ));
        }

        parseTagsForUpdate(argMultimap.getAllValues(CliSyntax.PREFIX_TAG))
                .ifPresent(updateTransactionDescriptor::setTags);

        if (!updateTransactionDescriptor.isAnyFieldUpdated()) {
            throw new ParseException(UpdateCommand.MESSAGE_NOT_UPDATED);
        }

        return new UpdateCommand(index, updateTransactionDescriptor);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>} if {@code tags} is non-empty.
     * If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<Tag>} containing zero tags.
     */
    private Optional<Set<Tag>> parseTagsForUpdate(Collection<String> tags) throws ParseException {
        assert tags != null;

        if (tags.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> tagSet = tags.size() == 1 && tags.contains("") ? Collections.emptySet() : tags;
        return Optional.of(ParserUtil.parseTags(tagSet));
    }

}
