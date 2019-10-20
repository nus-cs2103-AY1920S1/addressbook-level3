package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.savenus.logic.commands.RemoveDislikeCommand;
import seedu.savenus.logic.commands.RemoveLikeCommand;
import seedu.savenus.logic.commands.RemovePreferenceCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.tag.Tag;

/**
 * Parses input arguments and creates a new RemoveLikeCommand or RemoveDislikeCommand object
 */
public class RemovePreferenceCommandParser implements Parser<RemovePreferenceCommand> {

    @Override
    public RemovePreferenceCommand parse(String args) {
        throw new AssertionError("This method should not be called.");
    }

    /**
     * Parses the given {@code String} of arguments in the context of the PreferenceCommand
     * and returns A LikeCommand or DislikeCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemovePreferenceCommand parse(String args, boolean isLike) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_TAG, PREFIX_LOCATION);

        if (args.isBlank() && isLike) {
            return new RemoveLikeCommand(true);
        } else if (args.isBlank() && !isLike) {
            return new RemoveDislikeCommand(true);
        }

        // If none of these arguments are not present, will throw an error.
        if (!areAnyPrefixesPresent(argMultimap, PREFIX_CATEGORY, PREFIX_TAG, PREFIX_LOCATION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    RemovePreferenceCommand.MESSAGE_USAGE));
        }

        Set<Category> categoryList = ParserUtil.parseCategories(argMultimap.getAllValues(PREFIX_CATEGORY));
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<Location> locationList = ParserUtil.parseLocations(argMultimap.getAllValues(PREFIX_LOCATION));

        if (isLike) {
            return new RemoveLikeCommand(categoryList, tagList, locationList, false);
        } else {
            return new RemoveDislikeCommand(categoryList, tagList, locationList, false);
        }
    }

    /**
     * Returns true if any of the prefixes contains values in the given {@code ArgumentMultimap}.
     */
    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
