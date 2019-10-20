package thrift.model.transaction;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Calendar;

import org.junit.jupiter.api.Test;


public class BudgetTest {

    @Test
    public void constructor_nullCalendarNullValue_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Budget(null, null));
    }

    @Test
    public void constructor_validCalendarInvalidValue_throwsIllegalArgumentException() {
        Calendar now = Calendar.getInstance();
        assertThrows(IllegalArgumentException.class, () -> new Budget(now, new BudgetValue("-100")));
    }

    @Test
    public void compareIdenticalBudgets_returnsTrue() {
        Calendar now = Calendar.getInstance();
        Budget b1 = new Budget(now, new BudgetValue("123"));
        Budget b2 = new Budget(now, new BudgetValue("123"));
        assertEquals(b1, b2);
    }

}
