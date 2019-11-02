package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ReminderCommand;

public class ReminderCommandParserTest {
    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parseTest() {

        assertParseSuccess(parser, "test d/7",
                new ReminderCommand("test", 7));

        assertParseSuccess(parser, "test",
                new ReminderCommand("test", 7));

    }
}
