package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.commands.FindCommand;
import seedu.flashcard.model.flashcard.FlashcardContainsKeywordsPredicate;


public class FindCommandParserTest {
    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT
                + FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new FlashcardContainsKeywordsPredicate(Arrays.asList("BANANA", "APPLE")));
        assertParseSuccess(parser, "BANANA APPLE", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n BANANA \n \t APPLE  \t", expectedFindCommand);
    }
}
