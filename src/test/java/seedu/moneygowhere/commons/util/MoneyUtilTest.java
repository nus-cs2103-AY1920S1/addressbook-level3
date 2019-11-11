package seedu.moneygowhere.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MoneyUtilTest {

    @Test
    public void format_validInput_returnsTrue() {
        assertTrue(MoneyUtil.format(1000000.00).equals("1M"));
        assertTrue(MoneyUtil.format(1000000000.00).equals("1B"));
        assertTrue(MoneyUtil.format(1000000000000.00).equals("1T"));
        assertTrue(MoneyUtil.format(1000.00).equals("1000.00"));
        assertTrue(MoneyUtil.format(999.00).equals("999.00"));
    }

    @Test
    public void format_invalidInput_returnsFalse() {
        assertFalse(MoneyUtil.format(1000).equals("1000"));
        assertFalse(MoneyUtil.format(10000).equals("10000"));
    }
}
