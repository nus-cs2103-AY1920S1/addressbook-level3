package seedu.address.logic.parser.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.tag.TagCommand;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.quiz.QuizCommandParser;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new TagCommand object.
 */
public class TagCommandParser implements Parser<TagCommand> {

    private static final Logger logger = LogsCenter.getLogger(TagCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
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
                logger.info("tag set cannot be empty");
                throw new ParseException(TagCommand.MESSAGE_NOT_TAGGED);
            }
        } catch (ParseException pe) {
            logger.info("error parsing tags");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE, pe.getMessage()),
                    pe);
        }


        return new TagCommand(index, tagSet);
    }
}
