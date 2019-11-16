package seedu.ifridge.logic.parser.grocerylist;

import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.ifridge.logic.commands.grocerylist.FindGroceryCommand;
import seedu.ifridge.logic.parser.Parser;
import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.model.food.NameContainsKeywordsPredicate;
import seedu.ifridge.model.food.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindGroceryCommand object
 */
public class FindGroceryCommandParser implements Parser<FindGroceryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindGroceryCommand
     * and returns a FindGroceryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindGroceryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindGroceryCommand.MESSAGE_USAGE));
        }

        String[] keywords = trimmedArgs.split("\\s+");

        return new FindGroceryCommand(new NameContainsKeywordsPredicate(Arrays.asList(keywords)),
                new TagContainsKeywordsPredicate(Arrays.asList(keywords)));
    }

}
