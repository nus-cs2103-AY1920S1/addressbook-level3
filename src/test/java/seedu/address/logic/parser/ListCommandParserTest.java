package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ACTIVITY_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.ListCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ACTIVITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CONTACT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandSubType;
import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {
    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_listTypeMissing_failure() {
        // Empty command
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // All irrelevant fields
        assertParseFailure(parser, " " + PREFIX_TITLE + VALID_ACTIVITY_TITLE + NAME_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_bothListTypesPresent_failure() {
        // Both type fields c/ and a/
        assertParseFailure(parser, " " + PREFIX_CONTACT + " " + PREFIX_ACTIVITY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_oneListTypePresent_success() {
        // Only type field c/
        assertParseSuccess(parser, " " + PREFIX_CONTACT, new ListCommand(CommandSubType.CONTACT));

        // Multiple of type field a/
        assertParseSuccess(parser, " " + PREFIX_ACTIVITY + " " + PREFIX_ACTIVITY,
                new ListCommand(CommandSubType.ACTIVITY));
    }

    @Test
    public void parse_preamblePresent_failure() {
        // Irrelevant field (preamble) followed by type field
        assertParseFailure(parser, NAME_DESC_AMY + " " + PREFIX_CONTACT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_oneListSubTypeWithArg_successWithArgIgnored() {
        // Type field a/ with non-empty arg value
        assertParseSuccess(parser, " " + PREFIX_ACTIVITY + VALID_PHONE_AMY,
                new ListCommand(CommandSubType.ACTIVITY));

        // Type field c/ followed by irrelevant field (string arg)
        assertParseSuccess(parser, " " + PREFIX_CONTACT + NAME_DESC_AMY,
                new ListCommand(CommandSubType.CONTACT));
    }
}
