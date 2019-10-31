//@@author shutingy-reused

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ListCategoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;

/**
 * Parses input arguments and creates a new ListCategoryCommand object
 */
public class FindCategoryCommandParser implements Parser<ListCategoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListCategoryCommand
     * and returns a ListCategoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCategoryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCategoryCommand.MESSAGE_USAGE));
        }

        String[] questionKeywords = trimmedArgs.split("\\s+");

        return new ListCategoryCommand(new CategoryContainsAnyKeywordsPredicate(Arrays.asList(questionKeywords)));
    }
}
