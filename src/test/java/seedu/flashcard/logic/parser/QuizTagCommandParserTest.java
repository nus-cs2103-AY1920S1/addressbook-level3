package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_LONG;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_ROUND;
import static seedu.flashcard.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.commands.QuizTagCommand;
import seedu.flashcard.model.tag.Tag;

public class QuizTagCommandParserTest {

    private QuizTagCommandParser parser = new QuizTagCommandParser();

    @Test
    public void parse_validArgs_returnsQuizTagCommand() {
        String inputMessage = " " + PREFIX_TAG + VALID_TAG_ROUND + " " + PREFIX_TAG + VALID_TAG_LONG;
        Tag firstTag = new Tag(VALID_TAG_ROUND);
        Tag secondTag = new Tag(VALID_TAG_LONG);
        Set<Tag> inputTags = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(firstTag, secondTag)));
        assertParseSuccess(parser, inputMessage, new QuizTagCommand(inputTags, 1));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "t/#12345",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT + QuizTagCommand.MESSAGE_USAGE));
    }
}
