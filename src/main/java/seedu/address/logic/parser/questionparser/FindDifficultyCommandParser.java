package seedu.address.logic.parser.questionparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.questioncommands.FindDifficultyCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.question.DifficultyContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindDifficultyCommand object
 */
public class FindDifficultyCommandParser implements Parser<FindDifficultyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindDifficultyCommand
     * and returns a FindDifficultyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDifficultyCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDifficultyCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindDifficultyCommand(new DifficultyContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
