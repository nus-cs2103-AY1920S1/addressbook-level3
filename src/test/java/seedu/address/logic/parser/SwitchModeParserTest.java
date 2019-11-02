package seedu.address.logic.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.switches.SwitchToOpenCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SwitchModeParserTest {

    private SpecificModeParser specificModeParser;

    @BeforeEach
    void setUp() {
        specificModeParser = new SpecificModeParser();
        specificModeParser.add(SwitchToOpenCommand.class, null);
    }

    @Test
    void add() {

    }

    @Test
    void parseCommand() {
        try {
            Command out = specificModeParser.parseCommand("open");
            assertTrue(out instanceof SwitchToOpenCommand);
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    @Test
    void getAutoFill() {
        List<AutoFillAction> out = specificModeParser.getAutoFill("open");
        boolean foundMatch = false;
        for (AutoFillAction a : out) {
            if (a.toString().equals("open")) {
                foundMatch = true;
            }
        }
        assertTrue(foundMatch);
    }
}