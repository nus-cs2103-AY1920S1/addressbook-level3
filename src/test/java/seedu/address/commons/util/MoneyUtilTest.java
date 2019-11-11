package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.order.Price;
import seedu.address.model.phone.Cost;


class MoneyUtilTest {

    @Test
    void convertToDouble_nullPrice_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> MoneyUtil.convertToDouble((Price) null));
    }

    @Test
    void convertToDouble_nullCost_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> MoneyUtil.convertToDouble((Cost) null));
    }

    @Test
    void convertToDouble_validPrice_returnsCorrespondingDouble() {
        Price validPrice = new Price("$1.00");
        assertEquals(MoneyUtil.convertToDouble(validPrice), 1.00);
    }

    @Test
    void convertToDouble_validCost_returnsCorrespondingDouble() {
        Cost validCost = new Cost("$0.10");
        assertEquals(MoneyUtil.convertToDouble(validCost), 0.10);
    }

}
