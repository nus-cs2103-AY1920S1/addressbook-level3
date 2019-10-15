package seedu.savenus.model.info;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class BudgetInfoTest {

    @Test
    public void budget_correctCommandWord() {
        assertTrue(BudgetInfo.COMMAND_WORD.equals("budget"));
    }

    @Test
    public void budget_wrongCommandWord() {
        assertFalse(BudgetInfo.COMMAND_WORD.equals("epic"));
    }

    @Test
    public void budget_wrongInformation() {
        assertFalse(BudgetInfo.INFORMATION.isEmpty());
    }

    @Test
    public void budget_correctUsage() {
        assertTrue(BudgetInfo.USAGE.equals("Budget 100 23"));
    }
}
