package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESC;

import java.util.stream.Stream;

import seedu.address.logic.commands.DeleteCategoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Category;

/**
 * Parses input arguments and creates a new DeleteCategoryCommand object
 */
public class DeleteCategoryCommandParser implements Parser<DeleteCategoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCategoryCommand
     * and returns a DeleteCategoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCategoryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_CATEGORY, PREFIX_DESC);

        if (!arePrefixesPresent(argMultimap, PREFIX_CATEGORY, PREFIX_DESC)
                    || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                        DeleteCategoryCommand.MESSAGE_USAGE));
        }

        String categoryType = argMultimap.getValue(PREFIX_CATEGORY).get().toLowerCase();
        String categoryName = argMultimap.getValue(PREFIX_DESC).get().toLowerCase();

        //will check if category exist when creating new category
        return new DeleteCategoryCommand(new Category(categoryName, categoryType));
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
