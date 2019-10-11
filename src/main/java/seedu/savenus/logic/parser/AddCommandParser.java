package seedu.savenus.logic.parser;

import static seedu.savenus.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_OPENING_HOURS;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_RESTRICTIONS;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.savenus.logic.commands.AddCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.food.Category;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Location;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.OpeningHours;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.food.Restrictions;
import seedu.savenus.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRICE, PREFIX_DESCRIPTION,
                        PREFIX_CATEGORY, PREFIX_TAG, PREFIX_LOCATION, PREFIX_OPENING_HOURS, PREFIX_RESTRICTIONS);

        // If these arguments are not present, will throw an error as they are mandatory.
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PRICE, PREFIX_CATEGORY)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        // Name and price are required fields
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());
        Category category = ParserUtil.parseCategory(argMultimap.getValue(PREFIX_CATEGORY).get());

        // Description is an optional field
        Description description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION)
                .orElse(Description.DEFAULT_VALUE));

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        // Location is an optional field
        Location location = ParserUtil.parseLocation(argMultimap.getValue(PREFIX_LOCATION)
                .orElse(Location.DEFAULT_VALUE));

        // Opening Hours is an optional field.
        OpeningHours openingHours = ParserUtil.parseOpeningHours(argMultimap.getValue(PREFIX_OPENING_HOURS)
                .orElse(OpeningHours.DEFAULT_VALUE));

        // Restriction is an optional field.
        Restrictions restrictions = ParserUtil.parseRestrictions(argMultimap.getValue(PREFIX_RESTRICTIONS)
                .orElse(Restrictions.DEFAULT_VALUE));


        Food food = new Food(name, price, description, category, tagList, location, openingHours, restrictions);

        return new AddCommand(food);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
