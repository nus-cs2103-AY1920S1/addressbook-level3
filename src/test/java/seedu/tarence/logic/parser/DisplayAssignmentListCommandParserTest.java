package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_TUTORIAL_INDEX_DESC_1;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_TUTORIAL_INDEX_DESC_2;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_TUTORIAL_INDEX_DESC_3;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.DisplayAssignmentListCommand;

public class DisplayAssignmentListCommandParserTest {

    private DisplayAssignmentListCommandParser parser = new DisplayAssignmentListCommandParser();

    @Test
    public void parse_validArgs_returnsListCommand() {
        String validListInput = "1";
        DisplayAssignmentListCommand expectedListCommand =
                new DisplayAssignmentListCommand(INDEX_FIRST_IN_LIST);
        assertParseSuccess(parser, validListInput, expectedListCommand);
    }

    @Test
    public void parse_invalidArgs_returnsListCommand() {
        String invalidIndexMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DisplayAssignmentListCommand.MESSAGE_USAGE);
        // Invalid index - non-integer
        assertParseFailure(parser, INVALID_TUTORIAL_INDEX_DESC_1, invalidIndexMessage);

        // Invalid index - character
        assertParseFailure(parser, INVALID_TUTORIAL_INDEX_DESC_2, invalidIndexMessage);

        // Invalid index - Index <= 0
        assertParseFailure(parser, INVALID_TUTORIAL_INDEX_DESC_3, invalidIndexMessage);
    }
}
