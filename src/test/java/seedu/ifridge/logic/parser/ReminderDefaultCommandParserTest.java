package seedu.ifridge.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ifridge.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import org.junit.jupiter.api.Test;

import seedu.ifridge.logic.commands.Command;
import seedu.ifridge.logic.commands.defaults.ReminderDefaultCommand;
import seedu.ifridge.logic.parser.defaults.ReminderDefaultCommandParser;
import seedu.ifridge.logic.parser.exceptions.ParseException;

public class ReminderDefaultCommandParserTest {
    private ReminderDefaultCommandParser parser = new ReminderDefaultCommandParser();

    @Test
    public void parse_validValueSpecified_success() {
        // valid number of days
        try {
            Command command = parser.parse(" r/0");
            Command expectedCommand = new ReminderDefaultCommand("0");

            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid number of days
        try {
            parser.parse(" r/-1");
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            assertEquals(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ReminderDefaultCommand.MESSAGE_USAGE), pe.getMessage());
        }
    }
}
