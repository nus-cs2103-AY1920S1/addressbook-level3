package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.flashcard.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.commands.QuizCommand;

public class QuizCommandParserTest {

    private QuizCommandParser parser = new QuizCommandParser();

    @Test
    public void parse_validArgs_returnsQuizCommand() {
        assertParseSuccess(parser, "1", new QuizCommand(INDEX_FIRST_FLASHCARD, 1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT + QuizCommand.MESSAGE_USAGE));
    }
}
