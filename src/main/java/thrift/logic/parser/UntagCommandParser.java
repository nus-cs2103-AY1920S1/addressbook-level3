package thrift.logic.parser;

import static java.util.Objects.requireNonNull;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE;

import java.util.Set;

import thrift.commons.core.index.Index;
import thrift.logic.commands.UntagCommand;
import thrift.logic.parser.exceptions.ParseException;
import thrift.model.tag.Tag;

/**
 * Parses input arguments and creates a new UntagCommand object.
 */
public class UntagCommandParser implements Parser<UntagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the UntagCommand
     * and returns an UntagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public UntagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_INDEX, CliSyntax.PREFIX_TAG);

        Index index;
        Set<Tag> tagSet;

        try {
            String indexStr = argMultimap.getSingleValue(CliSyntax.PREFIX_INDEX)
                    .orElseThrow(() -> new ParseException(""));
            index = ParserUtil.parseIndex(indexStr);
            tagSet = ParserUtil.parseTags(argMultimap.getAllValues(CliSyntax.PREFIX_TAG));
            if (tagSet.isEmpty()) {
                throw new ParseException(UntagCommand.MESSAGE_NOT_UNTAGGED);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT_WITH_PE, UntagCommand.MESSAGE_USAGE, pe.getMessage()),
                    pe);
        }

        if (tagSet.isEmpty()) {
            throw new ParseException(UntagCommand.MESSAGE_NOT_UNTAGGED);
        }

        return new UntagCommand(index, tagSet);
    }
}
