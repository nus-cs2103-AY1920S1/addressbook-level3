package dukecooks.storage.dashboard;

import static dukecooks.testutil.dashboard.TypicalDashboard.TASK2;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import dukecooks.commons.exceptions.IllegalValueException;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.model.dashboard.components.DashboardName;
import dukecooks.testutil.Assert;

public class JsonAdaptedDashboardTest {
    private static final String INVALID_NAME = "Bake a @ake";
    private static final String INVALID_DATE = "13.12.12";

    private static final String VALID_NAME = TASK2.getDashboardName().fullName;
    private static final String VALID_DATE = TASK2.getTaskDate().taskDate;

    @Test
    public void toModelType_validDashboardDetails_returnsDashboard() throws Exception {
        JsonAdaptedDashboard dashboard = new JsonAdaptedDashboard(TASK2);
        Dashboard m = dashboard.toModelType();
        assertEquals(m, m);
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedDashboard dashboard =
                new JsonAdaptedDashboard(INVALID_NAME, VALID_DATE, "NOT COMPLETE");
        String expectedMessage = DashboardName.MESSAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, dashboard::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedDashboard dashboard = new JsonAdaptedDashboard(null, VALID_DATE,
                "NOT COMPLETE");
        String expectedMessage = String.format(JsonAdaptedDashboard.MISSING_FIELD_MESSAGE_FORMAT,
                DashboardName.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, dashboard::toModelType);
    }

}
