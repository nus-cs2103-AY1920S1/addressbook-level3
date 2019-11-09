package seedu.ifridge.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.defaults.ReminderDefaultCommand;
import seedu.ifridge.logic.parser.defaults.ReminderDefaultCommandParser;
import seedu.ifridge.logic.parser.exceptions.ParseException;

public class ReminderDefaultCommandParserTest {
    @Test
    public void parse_validValueSpecified_success() {
        ReminderDefaultCommandParser parser = new ReminderDefaultCommandParser();
        // valid number of days
        try {
            Command command = parser.parse(" r/0");
            Command expectedCommand = new ReminderDefaultCommand("0");

            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }
}
