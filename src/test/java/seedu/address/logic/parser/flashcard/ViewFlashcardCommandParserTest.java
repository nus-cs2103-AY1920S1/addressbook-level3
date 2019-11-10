package seedu.address.logic.parser.flashcard;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_FLASHCARD;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.flashcard.ViewFlashcardCommand;

public class ViewFlashcardCommandParserTest {
    private ViewFlashcardCommandParser parser = new ViewFlashcardCommandParser();

    @Test
    public void parse_validArgs_returnsViewNoteCommand() {
        assertParseSuccess(parser, "1", new ViewFlashcardCommand(INDEX_FIRST_FLASHCARD));
    }

    @Test
    public void parse_validArgsWithAdditionalWhitespace_returnsViewNoteCommand() {
        assertParseSuccess(parser, "     1", new ViewFlashcardCommand(INDEX_FIRST_FLASHCARD));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewFlashcardCommand.MESSAGE_USAGE));
    }
}
