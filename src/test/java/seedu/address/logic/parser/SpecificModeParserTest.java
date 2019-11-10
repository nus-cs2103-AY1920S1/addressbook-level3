// @@author sreesubbash
package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.switches.SwitchToHomeCommand;
import seedu.address.logic.commands.switches.SwitchToOpenCommand;
import seedu.address.logic.commands.switches.SwitchToSettingsCommand;
import seedu.address.logic.commands.switches.SwitchToStartCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.AutoFillAction;

class SpecificModeParserTest {

    private SpecificModeParser switchParser;

    @BeforeEach
    void setUp() {
        switchParser = new SpecificModeParser();
        switchParser.add(SwitchToOpenCommand.class, null);
        switchParser.add(SwitchToHomeCommand.class, null);
        switchParser.add(SwitchToStartCommand.class, StartCommandParser.class);
        switchParser.add(SwitchToSettingsCommand.class, null);
    }

    @Test
    void parseCommandHome() {
        try {
            Command out = switchParser.parseCommand("home");
            assertTrue(out instanceof SwitchToHomeCommand);
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    @Test
    void getAutoFillHome() {
        List<AutoFillAction> out = switchParser.getAutoFill("home");
        boolean foundMatch = false;
        for (AutoFillAction a : out) {
            if (a.toString().equals("home")) {
                foundMatch = true;
            }
        }
        assertTrue(foundMatch);
    }

    @Test
    void parseCommandOpen() {
        try {
            Command out = switchParser.parseCommand("open");
            assertTrue(out instanceof SwitchToOpenCommand);
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    @Test
    void getAutoFillOpen() {
        List<AutoFillAction> out = switchParser.getAutoFill("open");
        boolean foundMatch = false;
        for (AutoFillAction a : out) {
            if (a.toString().equals("open")) {
                foundMatch = true;
            }
        }
        assertTrue(foundMatch);
    }

    @Test
    void parseCommandStart() {
        try {
            Command out = switchParser.parseCommand("start");
            assertTrue(out instanceof SwitchToStartCommand);
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    @Test
    void getAutoFillStart() {
        List<AutoFillAction> out = switchParser.getAutoFill("start");
        boolean foundMatch = false;
        for (AutoFillAction a : out) {
            if (a.toString().equals("start")) {
                foundMatch = true;
            }
        }
        assertTrue(foundMatch);
    }

    @Test
    void parseCommandSettings() {
        try {
            Command out = switchParser.parseCommand("settings");
            assertTrue(out instanceof SwitchToSettingsCommand);
        } catch (ParseException e) {
            System.err.println(e);
        }
    }

    @Test
    void getAutoFillSettings() {
        List<AutoFillAction> out = switchParser.getAutoFill("settings");
        boolean foundMatch = false;
        for (AutoFillAction a : out) {
            if (a.toString().equals("settings")) {
                foundMatch = true;
            }
        }
        assertTrue(foundMatch);
    }

}
