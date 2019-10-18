package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODCODE_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME_DESC;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.tarence.logic.parser.CliSyntax.PREFIX_TUTORIAL_INDEX;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.DisplayAttendanceCommand;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;

public class DisplayAttendanceCommandParserTest {

    private DisplayAttendanceCommandParser parser = new DisplayAttendanceCommandParser();
    private ModCode validModCode = new ModCode(VALID_MODCODE);
    private TutName validTutName = new TutName(VALID_TUTORIAL_NAME);
    private String validDisplayCommand = VALID_TUTORIAL_NAME_DESC + VALID_MODCODE_DESC;

    @Test
    public void parse_validArgs_returnsListCommand() {
        // no leading and trailing whitespaces
        DisplayAttendanceCommand expectedDisplayAttendanceCommand =
                new DisplayAttendanceCommand(validModCode, validTutName);
        assertParseSuccess(parser, validDisplayCommand, expectedDisplayAttendanceCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + validDisplayCommand + " \n \t", expectedDisplayAttendanceCommand);
    }

    @Test
    public void parse_invalidArgs_returnsDisplayAttendanceCommand() {
        String invalidIndexMessage =
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayAttendanceCommand.MESSAGE_USAGE);

        // Parsing only tutorial
        assertParseFailure(parser, VALID_TUTORIAL_NAME_DESC, invalidIndexMessage);

        // Parsing only module
        assertParseFailure(parser, VALID_MODCODE_DESC, invalidIndexMessage);

        // Parsing invalid tutorial
        String invalidTutorial = PREFIX_TUTORIAL_INDEX + "@#!";
        assertParseFailure(parser, invalidTutorial, invalidIndexMessage);

        // Parsing invalid module
        String invalidModule = PREFIX_MODULE + "CSCS";
        assertParseFailure(parser, invalidModule, invalidIndexMessage);

        // Parsing invalid tutorial and module
        assertParseFailure(parser, invalidModule + invalidTutorial, invalidIndexMessage);

    }
}
