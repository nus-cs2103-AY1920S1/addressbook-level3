package io.xpire.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.TagCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagComparator;

/**
 * Parses input arguments and creates a new TagCommand object.
 */
public class TagCommandParser implements Parser<TagCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TagCommand
     * and returns an TagCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public TagCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] splitArgs = args.split("\\|", 2);
        Index index;
        try {
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (ParseException pe) {
            throw new ParseException(String
                    .format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), pe);
        }
        Set<Tag> set;
        TagCommand.TagItemDescriptor tagItemDescriptor = new TagCommand.TagItemDescriptor();
        if (hasTags(splitArgs)) {
            String trimmedTags = splitArgs[1].trim();
            String[] tags = trimmedTags.split("#");
            set = ParserUtil.parseTags(Arrays.asList(tags));
        } else {
            set = new TreeSet<>(new TagComparator());
        }
        tagItemDescriptor.setTags(set);
        return new TagCommand(index, tagItemDescriptor);
    }

    private static boolean hasTags(String...arguments) {
        return arguments.length > 1;
    }



}
