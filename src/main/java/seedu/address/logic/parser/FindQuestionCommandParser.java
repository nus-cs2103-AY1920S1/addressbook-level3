//@@author shutingy-reused

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.SearchQuestionCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.flashcard.QuestionContainsAnyKeywordsPredicate;

/**
 * Parses input arguments and creates a new SearchQuestionCommand object
 */
public class FindQuestionCommandParser implements Parser<SearchQuestionCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SearchQuestionCommand
     * and returns a SearchQuestionCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */

    public SearchQuestionCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchQuestionCommand.MESSAGE_USAGE));
        }
        String[] questionKeywords = trimmedArgs.split("\\s+");
        return new SearchQuestionCommand(new QuestionContainsAnyKeywordsPredicate(Arrays.asList(questionKeywords)));
    }
}
