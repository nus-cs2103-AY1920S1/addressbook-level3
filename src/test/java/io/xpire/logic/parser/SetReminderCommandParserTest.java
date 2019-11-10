package io.xpire.logic.parser;

import static io.xpire.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static io.xpire.logic.parser.CommandParserTestUtil.assertEqualsParseSuccess;
import static io.xpire.logic.parser.CommandParserTestUtil.assertParseFailure;
import static io.xpire.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static io.xpire.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import org.junit.jupiter.api.Test;

import io.xpire.logic.commands.SetReminderCommand;
import io.xpire.model.item.ReminderThreshold;

public class SetReminderCommandParserTest {

    private SetReminderCommandParser parser = new SetReminderCommandParser();

    @Test
    public void parse_validArgs_returnsSetReminderCommand() {
        assertEqualsParseSuccess(parser, " 1|1",
                new SetReminderCommand(INDEX_FIRST_ITEM, new ReminderThreshold("1")));
        assertEqualsParseSuccess(parser, " 1 | 1",
                new SetReminderCommand(INDEX_FIRST_ITEM, new ReminderThreshold("1")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {

        // missing argument
        assertParseFailure(parser, "2",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetReminderCommand.MESSAGE_USAGE));

        // invalid index
        assertParseFailure(parser, "0|3", MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_invalidReminderThreshold_throwsParseException() {
        assertParseFailure(parser, "1|abc", ReminderThreshold.MESSAGE_CONSTRAINTS);
    }
}
