package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PARTICIPANT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AMOUNT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.ViewCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandSubType;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.testutil.TypicalIndexes;

public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_viewTypeMissing_failure() {
        // Empty command
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // All irrelevant fields
        assertParseFailure(parser, VALID_AMOUNT_DESC + " " + PARTICIPANT_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_bothViewTypesPresent_failure() {
        // Both type fields c/ and a/ present
        assertParseFailure(parser, " " + PREFIX_CONTACT + "20 " + PREFIX_ACTIVITY + "40",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_oneViewTypeWithoutValidArg_failure() {
        // Type field c/ with non-empty arg value
        assertParseFailure(parser, " " + PREFIX_CONTACT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // Type field a/ with string as arg value
        assertParseFailure(parser, " " + PREFIX_ACTIVITY + VALID_ACTIVITY_TITLE,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // Type field c/ with non-positive integer as arg value
        assertParseFailure(parser, " " + PREFIX_CONTACT + "0",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_oneViewTypePresentWithValidArg_success() {
        // Only type field c/
        assertParseSuccess(parser, " " + PREFIX_ACTIVITY + "3",
                new ViewCommand(CommandSubType.ACTIVITY, TypicalIndexes.INDEX_THIRD));

        // Multiple of type field c/
        assertParseSuccess(parser, " " + PREFIX_CONTACT + "0 " + PREFIX_CONTACT + "2 " + PREFIX_CONTACT + "1",
                new ViewCommand(CommandSubType.CONTACT, TypicalIndexes.INDEX_FIRST));

        // Multiple of type field c/ - last arg is valid
        assertParseSuccess(parser, " " + PREFIX_ACTIVITY + VALID_ACTIVITY_TITLE + " " + PREFIX_ACTIVITY + "2",
                new ViewCommand(CommandSubType.ACTIVITY, TypicalIndexes.INDEX_SECOND));
    }

    @Test
    public void parse_preamblePresent_failure() {
        // Irrelevant field (preamble) followed by type field
        assertParseFailure(parser, VALID_PHONE_AMY + " " + PREFIX_CONTACT + "2103",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
