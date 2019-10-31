package budgetbuddy.model.loan;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void contains() {
        assertTrue(Status.contains("PAID"));
        assertTrue(Status.contains("UNPAID"));
        assertFalse(Status.contains(null));
        assertFalse(Status.contains(""));
        assertFalse(Status.contains("GIBBERISH"));
    }
}
