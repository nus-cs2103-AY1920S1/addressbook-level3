package seedu.revision.logic.parser.main;

import static seedu.revision.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.revision.logic.commands.main.FindCommand;
import seedu.revision.logic.parser.Parser;
import seedu.revision.logic.parser.exceptions.ParseException;
import seedu.revision.model.answerable.predicates.QuestionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] questionKeywords = trimmedArgs.split("\\s+");

        return new FindCommand(new QuestionContainsKeywordsPredicate(Arrays.asList(questionKeywords)));
    }

}
