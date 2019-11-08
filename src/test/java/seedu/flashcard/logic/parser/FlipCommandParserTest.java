package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_ANSWER_APPLE;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.commands.FlipCommand;
import seedu.flashcard.model.flashcard.Answer;

public class FlipCommandParserTest {

    private FlipCommandParser parser = new FlipCommandParser();

    @Test
    public void parse_validArgs_returnsFlipCommand() {
        assertParseSuccess(parser, " " + VALID_ANSWER_APPLE, new FlipCommand(new Answer("Red")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT + FlipCommand.MESSAGE_USAGE));
    }
}
