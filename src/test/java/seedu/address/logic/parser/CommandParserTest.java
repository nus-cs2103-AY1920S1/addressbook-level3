package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.DeleteEventCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.parser.exceptions.ParseException;

class CommandParserTest {

    @Test
    void parse_invalidInputFormat_failure() {
        String[] tests = { "", " " };
        for (String test : tests) {
            assertThrows(ParseException.class, () -> new CommandParser().parse(test));
        }
    }

    @Test
    void parse_addEventCommand_success() throws ParseException {
        String test = "add_event 'Celebrate Rori Birthday' '18/08/2019 16:00'";
        assertTrue(new CommandParser().parse(test) instanceof AddEventCommand);
    }

    @Test
    void parse_deleteEventCommand_success() throws ParseException {
        String test = "delete_event 1 2 3";
        assertTrue(new CommandParser().parse(test) instanceof DeleteEventCommand);
    }

    @Test
    void parse_editEventCommand_success() throws ParseException {
        String test = "edit_event 1 2 3";
        assertTrue(new CommandParser().parse(test) instanceof EditEventCommand);
    }
}
