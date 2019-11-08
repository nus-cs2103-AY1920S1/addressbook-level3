package seedu.ifridge.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.grocerylist.ReminderCommand;
import seedu.ifridge.logic.parser.exceptions.ParseException;
import seedu.ifridge.logic.parser.grocerylist.ReminderCommandParser;


public class ReminderCommandParserTest {
    private ReminderCommandParser parser = new ReminderCommandParser();

    @Test
    public void parse_invalidValue_failure() {
        // invalid number of days
        try {
            parser.parse("r/-1", "3");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ReminderCommand.MESSAGE_USAGE), pe.getMessage());
        }
    }
}
