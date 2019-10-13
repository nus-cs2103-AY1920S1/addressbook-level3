package thrift.logic.parser;

import static java.util.Objects.requireNonNull;
import static thrift.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.HashSet;
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
        Set<Tag> tagSet = new HashSet<Tag>();

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreambleIncludeIndex());
            for (String tagName : argMultimap.getAllValues(CliSyntax.PREFIX_TAG)) {
                if (!tagName.isEmpty()) {
                    Tag tag = new Tag(tagName);
                    tagSet.add(tag);
                }
            }
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UntagCommand.MESSAGE_USAGE), pe);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(iae.getMessage(), iae);

        }

        if (tagSet.isEmpty()) {
            throw new ParseException(UntagCommand.MESSAGE_NOT_UNTAGGED);
        }

        return new UntagCommand(index, tagSet);
    }
}
