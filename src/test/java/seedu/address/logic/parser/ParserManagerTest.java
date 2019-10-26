package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.ModeSwitchException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.ModeEnum;

class ParserManagerTest {

    private ParserManager parserManager;

    @BeforeEach
    void setUp() {
        parserManager = new ParserManager();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getHomeMode() {
        assertTrue(parserManager.getMode() == ModeEnum.HOME);
    }


    @Test
    void getOpenMode() {
        try {
            parserManager.updateState(true, true);
            parserManager.parseCommand("open");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ModeSwitchException e) {
            e.printStackTrace();
        }
        System.err.println(parserManager.getMode());
        assertTrue(parserManager.getMode() == ModeEnum.OPEN);
    }

    @Test
    void getGameMode() {
        try {
            parserManager.parseCommand("bank sample");
            parserManager.updateState(true, true);
            parserManager.parseCommand("open");
            parserManager.parseCommand("start");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ModeSwitchException e) {
            e.printStackTrace();
        }
        System.err.println(parserManager.getMode());
        assertTrue(parserManager.getMode() == ModeEnum.GAME);
    }

    @Test
    void getSettingsMode() {
        try {
            parserManager.updateState(true, true);
            parserManager.parseCommand("settings");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (ModeSwitchException e) {
            e.printStackTrace();
        }
        System.err.println(parserManager.getMode());
        assertTrue(parserManager.getMode() == ModeEnum.SETTINGS);
    }

}
