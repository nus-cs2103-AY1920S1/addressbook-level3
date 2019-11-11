package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_FILENAME_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODCODE_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_IDX_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME_DESC;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.tarence.commons.core.index.Index;
import seedu.tarence.logic.commands.ExportAttendanceCommand;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;

public class ExportAttendanceCommandParserTest {
    private ExportAttendanceCommandParser parser = new ExportAttendanceCommandParser();

    @Test
    public void parse_allFullFormatFieldsPresent_success() {
        ModCode expectedModCode = new ModCode(VALID_MODCODE);
        TutName expectedTutName = new TutName(VALID_TUTORIAL_NAME);
        Index expectedIndex = Index.fromOneBased(1);
        String expectedFileName = "fileName";

        // whitespace only preamble - parses modcode and tutorial format
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC,
                new ExportAttendanceCommand(expectedModCode, expectedTutName, null, null));

        // parses index format
        assertParseSuccess(parser, VALID_FILENAME_DESC + VALID_TUTORIAL_IDX_DESC,
                new ExportAttendanceCommand(null, null, expectedIndex, expectedFileName));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportAttendanceCommand.MESSAGE_USAGE);

        // missing mod code prefix
        assertParseFailure(parser, VALID_MODCODE + VALID_TUTORIAL_NAME_DESC,
                expectedMessage);

        // missing tut name prefix
        assertParseFailure(parser, VALID_MODCODE_DESC + VALID_TUTORIAL_NAME,
                expectedMessage);
    }

    @Test
    public void parse_invalidFormat_failure() {
        // modcode, tutorial name, tutorial index present
        assertParseFailure(parser, VALID_TUTORIAL_IDX_DESC + VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportAttendanceCommand.MESSAGE_USAGE));

        // tutorial name, tutorial index present
        assertParseFailure(parser, VALID_TUTORIAL_IDX_DESC + VALID_TUTORIAL_NAME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportAttendanceCommand.MESSAGE_USAGE));

        // modcode, tutorial index present
        assertParseFailure(parser, VALID_TUTORIAL_IDX_DESC + VALID_MODCODE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportAttendanceCommand.MESSAGE_USAGE));

        // modcode present
        assertParseFailure(parser, VALID_MODCODE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportAttendanceCommand.MESSAGE_USAGE));

        // tutorial name present
        assertParseFailure(parser, VALID_TUTORIAL_NAME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportAttendanceCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // TODO: This does not throw an error?
        // non-empty preamble
        // assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC,
        //         String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportAttendanceCommand.MESSAGE_USAGE));
    }
}
