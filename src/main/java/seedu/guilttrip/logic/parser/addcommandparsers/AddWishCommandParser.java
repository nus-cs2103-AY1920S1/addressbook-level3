package seedu.guilttrip.logic.parser.addcommandparsers;

import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_AMOUNT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.guilttrip.logic.commands.addcommands.AddWishCommand;
import seedu.guilttrip.logic.parser.ArgumentMultimap;
import seedu.guilttrip.logic.parser.ArgumentTokenizer;
import seedu.guilttrip.logic.parser.Parser;
import seedu.guilttrip.logic.parser.ParserUtil;
import seedu.guilttrip.logic.parser.Prefix;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.model.tag.Tag;

/**
 * Parses input argument and creates a new AddWishCommand object.
 */
public class AddWishCommandParser implements Parser<AddWishCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddWishCommand
     * and returns an AddWishCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddWishCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_DESC, PREFIX_AMOUNT, PREFIX_DATE,
                        PREFIX_TAG);

        ParserUtil.errorIfCompulsoryPrefixMissing(AddWishCommand.MESSAGE_USAGE, argMultimap, false,
                PREFIX_CATEGORY, PREFIX_DESC, PREFIX_AMOUNT, PREFIX_DATE);

        String categoryName = argMultimap.getValue(PREFIX_CATEGORY).get();
        Description desc = ParserUtil.parseDescription(argMultimap.getValue(PREFIX_DESC).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Amount amt = ParserUtil.parseAmount(argMultimap.getValue(PREFIX_AMOUNT).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        Wish wish = new Wish(new Category(categoryName, "Expense"), desc, date, amt, tagList);

        return new AddWishCommand(wish);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
