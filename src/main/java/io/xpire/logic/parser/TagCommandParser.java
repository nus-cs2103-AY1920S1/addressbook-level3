package io.xpire.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import io.xpire.commons.core.Messages;
import io.xpire.commons.core.index.Index;
import io.xpire.logic.commands.TagCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.ListType;
import io.xpire.model.tag.Tag;
import io.xpire.model.tag.TagItemDescriptor;

/**
 * @@author Kalsyc
 * Parses input arguments and creates a new TagCommand object.
 */
public class TagCommandParser implements Parser<TagCommand> {
    private final ListType listType;

    public TagCommandParser(ListType listType) {
        this.listType = listType;
    }

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
        if (splitArgs[0].isEmpty()) {
            return new TagCommand(listType);
        }
        try {
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (ParseException pe) {
            throw new ParseException(String
                    .format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), pe);
        }
        Set<Tag> set;
        TagItemDescriptor tagItemDescriptor = new TagItemDescriptor();
        if (hasTags(splitArgs)) {
            set = ParserUtil.parseTagsFromInput(splitArgs[1]);
        } else {
            throw new ParseException(String
                    .format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }
        tagItemDescriptor.setTags(set);
        return new TagCommand(listType, index, tagItemDescriptor);
    }

    private static boolean hasTags(String[] arguments) {
        return arguments.length > 1 && arguments[1].trim().split("#").length > 1;
    }
}
