package seedu.address.logic.parser;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.TagCommand.TagItemDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static java.util.Objects.requireNonNull;

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
        String[] splitArgs = args.split("\\|",2);
        Index index;
        try {
            index = ParserUtil.parseIndex(splitArgs[0]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE), pe);
        }
        Set<Tag> set;
        TagItemDescriptor tagItemDescriptor = new TagItemDescriptor();
        if (hasTags(splitArgs)) {
            String trimmedTags = splitArgs[1].trim();
            String[] tags = trimmedTags.split("#");
            set = ParserUtil.parseTags(Arrays.asList(tags));
        } else {
            set = new HashSet<>();
        }
        tagItemDescriptor.setTags(set);
        return new TagCommand(index, tagItemDescriptor);
    }

    private static boolean hasTags(String...arguments) {
        return arguments.length > 1;
    }



}
