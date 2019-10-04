package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Set;

import io.xpire.logic.commands.AddCommand;
import io.xpire.logic.parser.exceptions.ParseException;
import io.xpire.model.item.ExpiryDate;
import io.xpire.model.item.Item;
import io.xpire.model.item.Name;
import io.xpire.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        String[] arguments = args.split("\\|", 3);
        if (!areArgumentsPresent(arguments)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(arguments[0]);
        ExpiryDate expiryDate = ParserUtil.parseExpiryDate(arguments[1]);
        Item item;
        if (hasTags(arguments)) {
            String trimmedTags = arguments[2].trim();
            String[] tags = trimmedTags.split("#");
            /* TODO:Need to change tests such that we prevent e from being tagged if user input = add|item|date|e#Tag1
            if (tags.length != 0) {
                tags = Arrays.copyOfRange(tags,1,tags.length);
            }
             */
            Set<Tag> tagSet = ParserUtil.parseTags(Arrays.asList(tags));
            item = new Item(name, expiryDate, tagSet);
        } else {
            item = new Item(name, expiryDate);
        }

        return new AddCommand(item);
    }

    private static boolean areArgumentsPresent(String...arguments) {
        return arguments.length >= 2;
    }

    private static boolean hasTags(String...arguments) {
        return arguments.length >= 3;
    }

}
