package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.ListPersonsCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.TagContainsKeywordsPredicate;

//@@author madanalogy
/**
 * Parses input arguments and creates a new FindPersonsCommand object
 */
public class ListPersonsCommandParser implements Parser<ListPersonsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListPersonsCommand
     * and returns a ListPersonsCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListPersonsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListPersonsCommand.MESSAGE_USAGE));
        }

        String[] tagKeywords = trimmedArgs.split("\\s+");

        return new ListPersonsCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

}
