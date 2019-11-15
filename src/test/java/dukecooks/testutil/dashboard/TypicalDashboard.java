package dukecooks.testutil.dashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dukecooks.logic.commands.CommandTestUtil;
import dukecooks.model.dashboard.DashboardRecords;
import dukecooks.model.dashboard.components.Dashboard;

/**
 * A utility class containing a list of {@code Dashboard} objects to be used in tests.
 */
public class TypicalDashboard {

    public static final Dashboard TASK1 = new DashboardBuilder().withDashboardName("Bake cupcakes")
            .withTaskDate("12/12/2019").withTaskStatus("COMPLETED").build();
    public static final Dashboard TASK2 = new DashboardBuilder().withDashboardName("Bake a cake")
            .withTaskDate("13/12/2019").withTaskStatus("NOT COMPLETE").build();
    public static final Dashboard TASK3 = new DashboardBuilder().withDashboardName("Go for a run")
            .withTaskDate("13/12/2019").withTaskStatus("NOT COMPLETE").build();
    public static final Dashboard TASK7 = new DashboardBuilder().withDashboardName("Project Meeting")
            .withTaskDate("13/12/2019").withTaskStatus("RECENTLY COMPLETED").build();

    public static final Dashboard TASK4COMPLETE = new DashboardBuilder().withDashboardName("Bake a cake")
            .withTaskDate("13/12/2019").withTaskStatus("COMPLETED").build();
    public static final Dashboard TASK5COMPLETE = new DashboardBuilder().withDashboardName("Go for a run")
            .withTaskDate("13/12/2019").withTaskStatus("COMPLETED").build();

    // Manually added - Dashboard's details found in {@code CommandTestUtil}
    public static final Dashboard TASK4 = new DashboardBuilder()
            .withDashboardName(CommandTestUtil.VALID_DASHBOARDNAME_RUN)
            .withTaskDate(CommandTestUtil.VALID_TASKDATE1).withTaskStatus(CommandTestUtil
                    .VALID_TASKSTATUS_COMPLETE).build();

    public static final Dashboard TASK5 = new DashboardBuilder()
            .withDashboardName(CommandTestUtil.VALID_DASHBOARDNAME_YOGA)
            .withTaskDate(CommandTestUtil.VALID_TASKDATE2).withTaskStatus(CommandTestUtil
                    .VALID_TASKSTATUS_COMPLETE).build();

    public static final Dashboard TASK6 = new DashboardBuilder()
            .withDashboardName(CommandTestUtil.VALID_DASHBOARDNAME_WRITE)
            .withTaskDate(CommandTestUtil.VALID_TASKDATE3).withTaskStatus(CommandTestUtil
                    .VALID_TASKSTATUS_INCOMPLETE).build();


    public static final String KEYWORD_MATCHING_MEIER = "Run"; // A keyword that matches RUN

    private TypicalDashboard() {} // prevents instantiation

    /**
     * Returns an {@code DashboardRecords} with all the typical dashboards.
     */
    public static DashboardRecords getTypicalDashboardRecords() {
        DashboardRecords dc = new DashboardRecords();
        for (Dashboard dashboard : getTypicalDashboard()) {
            dc.addDashboard(dashboard);
        }
        return dc;
    }

    public static List<Dashboard> getTypicalDashboard() {
        return new ArrayList<>(Arrays.asList(TASK1, TASK2, TASK3, TASK4, TASK5, TASK6));
    }
}
