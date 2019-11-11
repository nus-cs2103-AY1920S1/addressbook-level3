package seedu.savenus.model.savings;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.savenus.testutil.Assert.assertThrows;
import static seedu.savenus.testutil.TypicalSavingsHistory.getTypicalSavingsHistory;

import java.util.Collections;

import org.junit.jupiter.api.Test;


public class SavingsHistoryTest {

    private final SavingsHistory savingsHistory = new SavingsHistory();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), savingsHistory.getSavingsHistory());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savingsHistory.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyMenu_replacesData() {
        SavingsHistory newData = getTypicalSavingsHistory();
        savingsHistory.resetData(newData);
        assertEquals(newData, savingsHistory);
    }
}
