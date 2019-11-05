package seedu.guilttrip.logic.parser;

import static seedu.guilttrip.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.guilttrip.logic.parser.CliSyntax.PREFIX_DESC;

import java.util.stream.Stream;

import seedu.guilttrip.logic.commands.AddCategoryCommand;
import seedu.guilttrip.logic.parser.exceptions.ParseException;
import seedu.guilttrip.model.entry.Category;

/**
 * Parses input arguments and creates a new AddCategoryCommand object
 */
public class AddCategoryCommandParser implements Parser<AddCategoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCategoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCategoryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_DESC);

        if (!arePrefixesPresent(argMultimap, PREFIX_CATEGORY, PREFIX_DESC)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCategoryCommand.MESSAGE_USAGE));
        }

        String categoryType = argMultimap.getValue(PREFIX_CATEGORY).get().toLowerCase();
        String categoryName = argMultimap.getValue(PREFIX_DESC).get().toLowerCase();
        Category categoryToCreate = new Category(categoryName, categoryType);

        return new AddCategoryCommand(categoryToCreate);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
