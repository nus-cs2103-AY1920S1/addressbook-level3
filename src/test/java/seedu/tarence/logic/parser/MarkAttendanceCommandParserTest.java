package seedu.tarence.logic.parser;

import static seedu.tarence.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_MODCODE_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.tarence.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.tarence.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODCODE;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_MODCODE_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_TUTORIAL_NAME_DESC;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_WEEK;
import static seedu.tarence.logic.commands.CommandTestUtil.VALID_WEEK_DESC;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tarence.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.commands.MarkAttendanceCommand;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.person.Name;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Week;

public class MarkAttendanceCommandParserTest {
    private MarkAttendanceCommandParser parser = new MarkAttendanceCommandParser();

    @Test
    public void parse_allFullFormatFieldsPresent_success() {
        Name expectedStudName = new Name(VALID_NAME_AMY);
        ModCode expectedModCode = new ModCode(VALID_MODCODE);
        TutName expectedTutName = new TutName(VALID_TUTORIAL_NAME);
        Week expectedWeek = new Week(VALID_WEEK);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC
                + NAME_DESC_AMY + VALID_WEEK_DESC,
                new MarkAttendanceCommand(expectedModCode, expectedTutName, expectedWeek, expectedStudName));

        // multiple names - last name accepted
        assertParseSuccess(parser, VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC
                + NAME_DESC_AMY + VALID_WEEK_DESC + NAME_DESC_BOB + NAME_DESC_AMY,
                new MarkAttendanceCommand(expectedModCode,
                expectedTutName, expectedWeek, expectedStudName));

        // missing name
        assertParseSuccess(parser, VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC
                + VALID_WEEK_DESC,
                new MarkAttendanceCommand(expectedModCode, expectedTutName, expectedWeek, null));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE);

        // missing mod code prefix
        assertParseFailure(parser, VALID_MODCODE + VALID_TUTORIAL_NAME_DESC
                + NAME_DESC_AMY + VALID_WEEK_DESC,
                expectedMessage);

        // missing tut name prefix
        assertParseFailure(parser, VALID_MODCODE_DESC + VALID_TUTORIAL_NAME
                + NAME_DESC_AMY + VALID_WEEK_DESC,
                expectedMessage);

        // missing week prefix
        assertParseFailure(parser, VALID_MODCODE_DESC + VALID_TUTORIAL_NAME
                + NAME_DESC_AMY + VALID_WEEK,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC
                + VALID_WEEK_DESC, Name.MESSAGE_CONSTRAINTS);

        // two invalid values, only second invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + INVALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC
                + VALID_WEEK_DESC, ModCode.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + VALID_MODCODE_DESC + VALID_TUTORIAL_NAME_DESC
                + NAME_DESC_AMY + VALID_WEEK_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MarkAttendanceCommand.MESSAGE_USAGE));
    }
}
