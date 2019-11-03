package seedu.address.financialtracker.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ModelTest {

    @Test
    void updateFinancialTracker() {
        Model model = new Model();
        FinancialTracker ft = model.getFinancialTracker();

        // same object -> true
        assertTrue(model.getFinancialTracker().equals(ft));

        model.updateFinancialTracker(new FinancialTracker());
        // different object -> false
        assertFalse(model.getFinancialTracker().equals(ft));

    }
}