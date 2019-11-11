package unrealunity.visit.logic.parser;

import org.junit.jupiter.api.Test;

import unrealunity.visit.logic.commands.ReminderCommand;

public class ReminderCommandParserTest {
    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parseTest() {

        CommandParserTestUtil.assertParseSuccess(parser, "test d/7",
                new ReminderCommand("test", 7));

        CommandParserTestUtil.assertParseSuccess(parser, "test",
                new ReminderCommand("test", 7));

    }
}
