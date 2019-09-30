package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.item.ExpiryDate;
import seedu.address.model.item.Item;
import seedu.address.model.item.Name;
import seedu.address.model.tag.Tag;

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
            Set<Tag> tagList = ParserUtil.parseTags(Arrays.asList(tags));
            item = new Item(name, expiryDate, tagList);
        } else {
            item = new Item(name, expiryDate);
        }
        return new AddCommand(item);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private static boolean areArgumentsPresent(String...arguments) {
        return arguments.length >= 2;
    }

    private static boolean hasTags(String...arguments) {
        return arguments.length >= 3;
    }

}
