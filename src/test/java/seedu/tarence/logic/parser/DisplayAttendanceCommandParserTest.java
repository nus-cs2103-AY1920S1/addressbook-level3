package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_TUTORIAL_INDEX_FORMAT;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_TUTORIAL_INDEX_DESC_1;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_TUTORIAL_INDEX_DESC_2;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_TUTORIAL_INDEX_DESC_3;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODCODE_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_IDX_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME_DESC;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tarence.testutil.TypicalIndexes.INDEX_FIRST_IN_LIST;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.DisplayAttendanceCommand;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;

public class DisplayAttendanceCommandParserTest {

    private DisplayAttendanceCommandParser parser = new DisplayAttendanceCommandParser();
    private ModCode validModCode = new ModCode(VALID_MODCODE);
    private TutName validTutName = new TutName(VALID_TUTORIAL_NAME);
    private String validFullDisplayCommand = VALID_TUTORIAL_NAME_DESC + VALID_MODCODE_DESC;
    private String validIndexDisplayCommand = VALID_TUTORIAL_IDX_DESC;

    @Test
    public void parse_validArgs_returnsDisplayCommand() {
        // no leading and trailing whitespaces
        DisplayAttendanceCommand expectedDisplayAttendanceCommand =
                new DisplayAttendanceCommand(validModCode, validTutName);
        assertParseSuccess(parser, validFullDisplayCommand, expectedDisplayAttendanceCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(
                parser, " \n " + validFullDisplayCommand + " \n \t", expectedDisplayAttendanceCommand);
    }

    @Test
    public void parse_indexFormat_returnsDIsplayCommand() {
        DisplayAttendanceCommand expectedDisplayAttendanceCommand =
                new DisplayAttendanceCommand(INDEX_FIRST_IN_LIST);
        assertParseSuccess(parser, validIndexDisplayCommand, expectedDisplayAttendanceCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(
                parser, " \n " + validIndexDisplayCommand + " \n \t", expectedDisplayAttendanceCommand);
    }

    @Test
    public void parse_invalidArgs_returnsDisplayAttendanceCommand() {
        String invalidCommandMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayAttendanceCommand.MESSAGE_USAGE);

        // Parsing only tutorial - missing module
        assertParseFailure(parser, VALID_TUTORIAL_NAME_DESC, invalidCommandMessage);

        // Parsing only module - missing tutorial
        assertParseFailure(parser, VALID_MODCODE_DESC, invalidCommandMessage);

        // Parsing invalid tutorial
        String invalidTutorial = PREFIX_INDEX + "@#!";
        assertParseFailure(parser, invalidTutorial, invalidCommandMessage);

        // Parsing invalid module
        String invalidModule = PREFIX_MODULE + "CSCS";
        assertParseFailure(parser, invalidModule, invalidCommandMessage);

        // Parsing valid index, tutorial and module (ambiguous)
        assertParseFailure(parser,
                VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC + VALID_TUTORIAL_IDX_DESC,
                invalidCommandMessage);

        String invalidIndexExpectedMessage = String.format(MESSAGE_INVALID_TUTORIAL_INDEX_FORMAT,
                DisplayAttendanceCommand.MESSAGE_USAGE);

        // Parsing invalid index - non integer
        assertParseFailure(parser, INVALID_TUTORIAL_INDEX_DESC_1, invalidIndexExpectedMessage);

        // Parsing invalid index - character
        assertParseFailure(parser, INVALID_TUTORIAL_INDEX_DESC_2, invalidIndexExpectedMessage);

        // Parsing invalid index - <= 0
        assertParseFailure(parser, INVALID_TUTORIAL_INDEX_DESC_3, invalidIndexExpectedMessage);
    }
}
