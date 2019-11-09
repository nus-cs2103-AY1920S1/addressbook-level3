package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_DISPLAY_FORMAT;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_TUTORIAL_INDEX_FORMAT;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_DISPLAY_FORMAT_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_TUTORIAL_INDEX_DESC_1;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_ASSIGNMENT_NAME_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_DISPLAY_FORMAT_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_IDX_DESC;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.DisplayAssignmentScoreCommand;
import seedu.tarence.logic.commands.DisplayFormat;

public class DisplayAssignmentScoreCommandParserTest {

    private DisplayAssignmentScoreCommandParser parser = new DisplayAssignmentScoreCommandParser();
    private String validFullDisplayCommand = VALID_TUTORIAL_IDX_DESC + VALID_ASSIGNMENT_NAME_DESC
            + VALID_DISPLAY_FORMAT_DESC;

    @Test
    public void parse_validArgs_returnsDisplayAssignmentCommand() {
        // no leading and trailing whitespaces
        DisplayAssignmentScoreCommand expectedDisplayAssignmentCommand =
                new DisplayAssignmentScoreCommand(INDEX_FIRST_IN_LIST, VALID_ASSIGNMENT_NAME, DisplayFormat.GRAPH);
        assertParseSuccess(parser, validFullDisplayCommand, expectedDisplayAssignmentCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(
                parser, " \n " + validFullDisplayCommand + " \n \t", expectedDisplayAssignmentCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String invalidCommandMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayAssignmentScoreCommand.MESSAGE_USAGE);

        // Parsing nothing
        String nullCommand = "";
        assertParseFailure(parser, nullCommand, invalidCommandMessage);

        // Parsing only index
        assertParseFailure(parser, VALID_TUTORIAL_IDX_DESC, invalidCommandMessage);

        // Parsing only assignment name
        assertParseFailure(parser, VALID_ASSIGNMENT_NAME_DESC, invalidCommandMessage);

        // Parsing only display Format
        assertParseFailure(parser, VALID_DISPLAY_FORMAT_DESC, invalidCommandMessage);

        // Parsing invalid index
        String invalidIndexMessage = String.format(MESSAGE_INVALID_TUTORIAL_INDEX_FORMAT,
                DisplayAssignmentScoreCommand.MESSAGE_USAGE);
        String invalidIndexCommand = INVALID_TUTORIAL_INDEX_DESC_1 + VALID_ASSIGNMENT_NAME_DESC
                + VALID_DISPLAY_FORMAT_DESC;
        assertParseFailure(parser, invalidIndexCommand, invalidIndexMessage);

        // Parsing invalid assignment name
        String invalidAssignmentName = PREFIX_NAME + "#";
        String invalidNameCommand = VALID_TUTORIAL_IDX_DESC + invalidAssignmentName + VALID_DISPLAY_FORMAT_DESC;
        assertParseFailure(parser, invalidNameCommand, invalidCommandMessage);

        // Parsing invalid display format
        String invalidDisplayMessage = String.format(MESSAGE_INVALID_DISPLAY_FORMAT,
                DisplayAssignmentScoreCommand.MESSAGE_USAGE);
        String invalidDisplayFormatCommand = VALID_TUTORIAL_IDX_DESC
                + VALID_ASSIGNMENT_NAME_DESC + INVALID_DISPLAY_FORMAT_DESC;
        assertParseFailure(parser, invalidDisplayFormatCommand, invalidDisplayMessage);
    }
}
