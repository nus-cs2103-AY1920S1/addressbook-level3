package seedu.address.logic.parser;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.address.logic.util.ModeEnum;

import static org.junit.jupiter.api.Assertions.*;

class ParserManagerTest {

    ParserManager parserManager;

    @BeforeEach
    void setUp() {
        parserManager = new ParserManager();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getMode() {
        assertTrue(parserManager.getMode() == ModeEnum.HOME);
    }
}