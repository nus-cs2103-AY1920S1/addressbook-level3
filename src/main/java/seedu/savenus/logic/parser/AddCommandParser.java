package seedu.savenus.logic.parser;

import static seedu.savenus.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_PRICE;
import static seedu.savenus.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.savenus.logic.commands.AddCommand;
import seedu.savenus.logic.parser.exceptions.ParseException;
import seedu.savenus.model.food.Description;
import seedu.savenus.model.food.Food;
import seedu.savenus.model.food.Name;
import seedu.savenus.model.food.Price;
import seedu.savenus.model.tag.Tag;
import seedu.savenus.commons.core.Messages;

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
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PRICE, PREFIX_DESCRIPTION, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PRICE)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Price price = ParserUtil.parsePrice(argMultimap.getValue(PREFIX_PRICE).get());

        Description description;
        if (argMultimap.getValue(PREFIX_DESCRIPTION).isPresent()) {
            description = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESCRIPTION).get());
        } else {
            description = new Description("No description");
        }

        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Food food = new Food(name, price, description, tagList);

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
