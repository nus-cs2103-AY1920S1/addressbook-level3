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

    @Test
    public void dollarsToCents() {
        assertEquals(FineUtil.dollarsToCents(99.99), 9999);
        assertEquals(FineUtil.dollarsToCents(0.01), 1);
        assertEquals(FineUtil.dollarsToCents(12.00), 1200);
        assertEquals(FineUtil.dollarsToCents(743.12), 74312);
    }
}
