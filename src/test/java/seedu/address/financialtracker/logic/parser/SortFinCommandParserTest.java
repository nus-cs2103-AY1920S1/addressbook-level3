package seedu.address.financialtracker.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.financialtracker.logic.commands.SortFinCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;

class SortFinCommandParserTest {

    @Test
    public void parser_failure() {
        assertThrows(ParseException.class, () -> new SortFinCommandParser().parse("random_text"));
    }

    @Test
    public void parser_success() throws ParseException {
        // equal command generated regardless of casing
        Command command = new SortFinCommandParser().parse("tiMe");
        SortFinCommand sortfin = new SortFinCommand("TIME");
        assertTrue(sortfin.equals(command));

        // trailing spaces
        Command command2 = new SortFinCommandParser().parse(" tiMe   ");
        SortFinCommand sortfin2 = new SortFinCommand("TIME");
        assertTrue(sortfin2.equals(command2));
    }
}
