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

        Model model1 = new Model(new FinancialTracker());
        // different object -> false
        assertFalse(model1.getFinancialTracker().equals(ft));

    }
}