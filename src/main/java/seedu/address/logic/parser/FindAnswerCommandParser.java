//@@author shutingy-reused

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchAnswerCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flashcard.AnswerContainsAnyKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object
 */

public class FindAnswerCommandParser implements Parser<SearchAnswerCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchAnswerCommand
     * and returns a SearchAnswerCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchAnswerCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchAnswerCommand.MESSAGE_USAGE));
        }

        String[] questionKeywords = trimmedArgs.split("\\s+");

        return new SearchAnswerCommand(new AnswerContainsAnyKeywordsPredicate(Arrays.asList(questionKeywords)));
    }
}

