package seedu.address.overview.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalOverview.BLANK_OVERVIEW_MODEL;
import static seedu.address.testutil.TypicalOverview.OVERVIEW_MODEL_WITH_DATA;

import org.junit.jupiter.api.Test;

public class ModelManagerTest {

    @Test
    public void newModelManager_blankData() {
        Model model = new ModelManager(BLANK_OVERVIEW_MODEL);

        assertEquals(0.0, model.getBudgetTarget());
        assertEquals(0.0, model.getExpenseTarget());
        assertEquals(0.0, model.getSalesTarget());

        assertEquals(0.0, model.getBudgetThreshold());
        assertEquals(0.0, model.getExpenseThreshold());
        assertEquals(0.0, model.getSalesThreshold());

    }

    @Test
    public void newModelManager_withData() {
        Model model = new ModelManager(OVERVIEW_MODEL_WITH_DATA);

        assertEquals(1500.0, model.getBudgetTarget());
        assertEquals(500.0, model.getExpenseTarget());
        assertEquals(200.0, model.getSalesTarget());

        assertEquals(80.0, model.getBudgetThreshold());
        assertEquals(90.0, model.getExpenseThreshold());
        assertEquals(100.0, model.getSalesThreshold());

    }

    @Test
    public void setTargetFromBlank_correctInput_success() {
        Model model = new ModelManager(BLANK_OVERVIEW_MODEL);

        model.setBudgetTarget(500);
        assertEquals(500.0, model.getBudgetTarget());

        model.setExpenseTarget(50);
        assertEquals(50.0, model.getExpenseTarget());

        model.setSalesTarget(5);
        assertEquals(5.0, model.getSalesTarget());

    }

    @Test
    public void setTargetFromExisting_correctInput_success() {

        Model model = new ModelManager(OVERVIEW_MODEL_WITH_DATA);

        model.setBudgetTarget(500);
        assertEquals(500.0, model.getBudgetTarget());

        model.setExpenseTarget(50);
        assertEquals(50.0, model.getExpenseTarget());

        model.setSalesTarget(5);
        assertEquals(5.0, model.getSalesTarget());

    }

    @Test
    public void setThresholdFromBlank_correctInput_success() {
        Model model = new ModelManager(BLANK_OVERVIEW_MODEL);

        model.setBudgetThreshold(80);
        assertEquals(80.0, model.getBudgetThreshold());

        model.setExpenseThreshold(90);
        assertEquals(50.0, model.getExpenseThreshold());

        model.setSalesThreshold(100);
        assertEquals(5.0, model.getSalesThreshold());

    }

    @Test
    public void setThresholdFromExisting_correctInput_success() {

        Model model = new ModelManager(OVERVIEW_MODEL_WITH_DATA);

        model.setBudgetThreshold(80);
        assertEquals(80.0, model.getBudgetThreshold());

        model.setExpenseThreshold(90);
        assertEquals(50.0, model.getExpenseThreshold());

        model.setSalesThreshold(100);
        assertEquals(5.0, model.getSalesThreshold());

    }

    @Test
    public void checkNotifications_fromBlankModel_success() {
        Model model = new ModelManager(BLANK_OVERVIEW_MODEL);

        assertEquals(true, model.checkBudgetNotif());
        assertEquals(true, model.checkExpenseNotif());
        assertEquals(true, model.checkSalesNotif());
    }

    @Test
    public void checkNotifications_fromModelWithData_success() {
        Model model = new ModelManager(OVERVIEW_MODEL_WITH_DATA);

        assertEquals(true, model.checkBudgetNotif());
        assertEquals(true, model.checkExpenseNotif());
        assertEquals(true, model.checkSalesNotif());
    }

    @Test
    public void setNotifications_fromBlankModel_success() {
        Model model = new ModelManager(BLANK_OVERVIEW_MODEL);

        assertEquals(true, model.checkBudgetNotif());
        assertEquals(true, model.checkExpenseNotif());
        assertEquals(true, model.checkSalesNotif());

        model.setBudgetNotif(false);
        model.setExpenseNotif(false);
        model.setSalesNotif(false);

        assertEquals(false, model.checkBudgetNotif());
        assertEquals(false, model.checkExpenseNotif());
        assertEquals(false, model.checkSalesNotif());

    }

    @Test
    public void setNotifications_fromModelWithData_success() {
        Model model = new ModelManager(OVERVIEW_MODEL_WITH_DATA);

        assertEquals(true, model.checkBudgetNotif());
        assertEquals(true, model.checkExpenseNotif());
        assertEquals(true, model.checkSalesNotif());

        model.setBudgetNotif(false);
        model.setExpenseNotif(false);
        model.setSalesNotif(false);

        assertEquals(false, model.checkBudgetNotif());
        assertEquals(false, model.checkExpenseNotif());
        assertEquals(false, model.checkSalesNotif());

    }

}
