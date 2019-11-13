package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.FindLessonCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.lesson.LessonContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindLessonCommand object
 */
public class FindLessonCommandParser implements Parser<FindLessonCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindLessonCommand
     * and returns a FindLessonCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindLessonCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindLessonCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindLessonCommand(new LessonContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }
}
