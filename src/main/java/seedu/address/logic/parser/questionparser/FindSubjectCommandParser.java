package seedu.address.logic.parser.questionparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.questioncommands.FindSubjectCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.question.SubjectContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindSubjectCommand object
 */
public class FindSubjectCommandParser implements Parser<FindSubjectCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindSubjectCommand
     * and returns a FindSubjectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindSubjectCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSubjectCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindSubjectCommand(new SubjectContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
