package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BuyInfoTest {

    @Test
    public void buyInfo_differentClass() {
        BuyInfo buyInfo = new BuyInfo();
        assertFalse(buyInfo.equals(new BuyInfo()));
    }

    @Test
    public void buy_correctCommandWord() {
        assertTrue(BuyInfo.COMMAND_WORD.equals("buy"));
    }

    @Test
    public void buy_wrongInformation() {
        assertFalse(BuyInfo.INFORMATION.equals("anyh9ow"));
    }

    @Test
    public void buy_correctUsage() {
        assertTrue(BuyInfo.USAGE.contains("buy"));
    }

    @Test
    public void buy_wrongOutput() {
        assertFalse(BuyInfo.OUTPUT.equals("isduafgsdg"));
    }
}
