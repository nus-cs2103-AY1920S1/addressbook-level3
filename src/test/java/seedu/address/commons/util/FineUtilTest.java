package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FineUtilTest {

    @Test
    public void centsToDollarString() {
        assertEquals(FineUtil.centsToDollarString(500), "$5.00");
        assertEquals(FineUtil.centsToDollarString(0), "$0.00");
        assertEquals(FineUtil.centsToDollarString(1234), "$12.34");
    }
}
