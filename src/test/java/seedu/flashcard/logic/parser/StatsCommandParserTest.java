package seedu.flashcard.logic.parser;

import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.flashcard.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.flashcard.logic.commands.CommandTestUtil.TAG_DESC_LONG;
import static seedu.flashcard.logic.commands.CommandTestUtil.TAG_DESC_ROUND;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_LONG;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_ROUND;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.flashcard.model.tag.Tag.MESSAGE_CONSTRAINTS;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.commands.StatsCommand;
import seedu.flashcard.model.tag.Tag;





public class StatsCommandParserTest {
    private StatsCommandParser statsCommandParser = new StatsCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Set<Tag> testTags = new HashSet<>();
        Tag testTag1 = new Tag(VALID_TAG_ROUND);
        Tag testTag2 = new Tag(VALID_TAG_LONG);
        testTags.add(testTag1);
        testTags.add(testTag2);
        StatsCommand statsCommand = new StatsCommand(testTags);
        assertParseSuccess(statsCommandParser, PREAMBLE_WHITESPACE + TAG_DESC_ROUND + TAG_DESC_LONG,
                statsCommand);
    }

    @Test
    public void parse_noTags_success() {
        assertParseSuccess(statsCommandParser, "", new StatsCommand(null));
    }


    @Test
    public void parse_emptyTag_failure() {
        assertParseFailure(statsCommandParser, INVALID_TAG_DESC, MESSAGE_CONSTRAINTS);
    }
}
