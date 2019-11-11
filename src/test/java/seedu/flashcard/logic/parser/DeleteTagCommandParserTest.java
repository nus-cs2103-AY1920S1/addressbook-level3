package seedu.flashcard.logic.parser;

import static seedu.flashcard.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.flashcard.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.flashcard.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.flashcard.logic.commands.CommandTestUtil.TAG_DESC_ROUND;
import static seedu.flashcard.logic.commands.CommandTestUtil.VALID_TAG_ROUND;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.flashcard.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.flashcard.model.tag.Tag.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;

import seedu.flashcard.logic.commands.DeleteTagCommand;
import seedu.flashcard.model.tag.Tag;



public class DeleteTagCommandParserTest {
    private DeleteTagCommandParser deleteTagCommandParser = new DeleteTagCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Tag testTag1 = new Tag(VALID_TAG_ROUND);

        DeleteTagCommand deleteTagCommand = new DeleteTagCommand(testTag1);
        assertParseSuccess(this.deleteTagCommandParser, PREAMBLE_WHITESPACE + TAG_DESC_ROUND,
                deleteTagCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        assertParseFailure(deleteTagCommandParser, "", MESSAGE_INVALID_COMMAND_FORMAT
                + DeleteTagCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_emptyTag_failure() {
        assertParseFailure(deleteTagCommandParser, INVALID_TAG_DESC, MESSAGE_CONSTRAINTS);
    }
}
