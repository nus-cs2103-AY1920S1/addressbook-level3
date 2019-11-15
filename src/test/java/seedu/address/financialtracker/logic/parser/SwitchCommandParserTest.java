package seedu.address.financialtracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.financialtracker.logic.commands.SwitchCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

class SwitchCommandParserTest {

    @Test
    public void parser_failure() {
        assertThrows(ParseException.class, () -> new SwitchCommandParser().parse("random_text"));
    }

    @Test
    public void parser_success() throws ParseException {
        // equal command generated regardless of casing
        Command command = new SwitchCommandParser().parse("Singapore");
        SwitchCommand switchCommand = new SwitchCommand("Singapore");
        assertTrue(switchCommand.equals(command));

        // trailing spaces
        Command command2 = new SwitchCommandParser().parse(" singapore   ");
        SwitchCommand switchCommand2 = new SwitchCommand("Singapore");
        assertTrue(switchCommand2.equals(command2));
    }
}
