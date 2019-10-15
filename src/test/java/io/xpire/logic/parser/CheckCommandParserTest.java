package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static io.xpire.logic.parser.CommandParserTestUtil.assertParseFailure;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.CheckCommand;
import io.xpire.model.item.ExpiringSoonPredicate;
import io.xpire.model.item.ReminderThresholdExceededPredicate;

public class CheckCommandParserTest {
    private CheckCommandParser parser = new CheckCommandParser();

    @Test
    public void parse_validArgs_returnsCheckCommand() {
        assertParseSuccess(parser, " 1", new CheckCommand(new ExpiringSoonPredicate(1)));
        assertParseSuccess(parser, "", new CheckCommand(new ReminderThresholdExceededPredicate()));
        assertParseSuccess(parser, " ", new CheckCommand(new ReminderThresholdExceededPredicate()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE)); // non-numeric characters
        assertParseFailure(parser, "-3",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE)); // non-positive integer
        assertParseFailure(parser, "1.5",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CheckCommand.MESSAGE_USAGE)); // non-integer number
    }
}
