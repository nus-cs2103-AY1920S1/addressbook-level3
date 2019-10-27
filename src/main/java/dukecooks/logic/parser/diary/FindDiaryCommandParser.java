package dukecooks.logic.parser.diary;

import dukecooks.logic.commands.diary.FindDiaryCommand;
import dukecooks.logic.parser.Parser;
import dukecooks.logic.parser.exceptions.ParseException;
import dukecooks.model.diary.components.DiaryNameContainsKeywordsPredicate;

import java.util.Arrays;

import static dukecooks.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new FindDiaryCommand object
 */
public class FindDiaryCommandParser implements Parser<FindDiaryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindDiaryCommand
     * and returns a FindDiaryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindDiaryCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDiaryCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindDiaryCommand(new DiaryNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
