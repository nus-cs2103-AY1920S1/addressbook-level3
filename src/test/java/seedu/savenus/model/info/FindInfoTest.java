package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FindInfoTest {

    @Test
    public void findInfo_differentClass() {
        FindInfo findInfo = new FindInfo();
        assertFalse(findInfo.equals(new FindInfo()));
    }

    @Test
    public void find_correctCommandWord() {
        assertTrue(FindInfo.COMMAND_WORD.equals("find"));
    }

    @Test
    public void find_wrongInformation() {
        assertFalse(FindInfo.INFORMATION.equals("find"));
    }

    @Test
    public void find_correctUsage() {
        assertTrue(FindInfo.USAGE.contains("find"));
    }

    @Test
    public void find_correctOutput() {
        assertTrue(FindInfo.OUTPUT.contains("list"));
    }
}
