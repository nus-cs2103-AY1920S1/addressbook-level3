package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class InfoInfoTest {

    @Test
    public void infoInfo_differentClass() {
        InfoInfo infoInfo = new InfoInfo();
        assertFalse(infoInfo.equals(new InfoInfo()));
    }

    @Test
    public void info_correctCommandWord() {
        assertTrue(InfoInfo.COMMAND_WORD.equals("info"));
    }

    @Test
    public void info_wrongInformation() {
        assertFalse(InfoInfo.INFORMATION.equals("empty"));
    }

    @Test
    public void info_correctUsage() {
        assertTrue(InfoInfo.USAGE.contains("info"));
    }

    @Test
    public void info_wrongOutput() {
        assertFalse(InfoInfo.OUTPUT.isEmpty());
    }
}
