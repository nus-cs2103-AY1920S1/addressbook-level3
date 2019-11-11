package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.flashcard.logic.commands.FindCommand;
import seedu.flashcard.logic.parser.exceptions.ParseException;
import seedu.flashcard.model.flashcard.FlashcardContainsKeywordsPredicate;

/**
 * Parse input arguments to generate a {@Code EditCommand}
 */
public class FindCommandParser implements Parser {

    /**
     * Parse the given context in the find command
     * @param args The user input
     * @return new find command
     */
    @Override
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT + FindCommand.MESSAGE_USAGE));
        }
        String[] wordKeywords = trimmedArgs.split("\\s+");
        return new FindCommand(new FlashcardContainsKeywordsPredicate(Arrays.asList(wordKeywords)));
    }
}
