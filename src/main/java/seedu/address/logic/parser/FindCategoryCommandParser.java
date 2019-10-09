package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindCategoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.CategoryContainsAnyKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCategoryCommand object
 */
public class FindCategoryCommandParser implements Parser<FindCategoryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCategoryCommand
     * and returns a FindCategoryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCategoryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCategoryCommand.MESSAGE_USAGE));
        }

        String[] questionKeywords = trimmedArgs.split("\\s+");

        return new FindCategoryCommand(new CategoryContainsAnyKeywordsPredicate(Arrays.asList(questionKeywords)));
    }
}
