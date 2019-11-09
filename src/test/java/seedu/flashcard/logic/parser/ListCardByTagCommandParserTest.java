package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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

import seedu.flashcard.logic.commands.ListCardByTagCommand;
import seedu.flashcard.model.tag.Tag;


public class ListCardByTagCommandParserTest {

    private ListCardByTagCommandParser listCardByTagCommandParser = new ListCardByTagCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Set<Tag> testTags = new HashSet<>();
        Tag testTag1 = new Tag(VALID_TAG_ROUND);
        Tag testTag2 = new Tag(VALID_TAG_LONG);
        testTags.add(testTag1);
        testTags.add(testTag2);
        ListCardByTagCommand listCardByTagCommand = new ListCardByTagCommand(testTags);
        assertParseSuccess(listCardByTagCommandParser, PREAMBLE_WHITESPACE + TAG_DESC_ROUND + TAG_DESC_LONG,
                listCardByTagCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        assertParseFailure(listCardByTagCommandParser, "", MESSAGE_INVALID_COMMAND_FORMAT
                + ListCardByTagCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_emptyTag_failure() {
        assertParseFailure(listCardByTagCommandParser, INVALID_TAG_DESC, MESSAGE_CONSTRAINTS);
    }
}
