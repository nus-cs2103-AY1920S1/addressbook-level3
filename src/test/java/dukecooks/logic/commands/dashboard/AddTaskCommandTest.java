package dukecooks.logic.commands.dashboard;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import dukecooks.logic.commands.CommandResult;
import dukecooks.logic.commands.exceptions.CommandException;
import dukecooks.model.ModelStub;
import dukecooks.model.dashboard.DashboardRecords;
import dukecooks.model.dashboard.ReadOnlyDashboard;
import dukecooks.model.dashboard.components.Dashboard;
import dukecooks.testutil.Assert;
import dukecooks.testutil.dashboard.DashboardBuilder;

public class AddTaskCommandTest {

    @Test
    public void constructor_nullDashboard_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new AddTaskCommand(null));
    }

    @Test
    public void execute_dashboardAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingDashboardAdded modelStub = new ModelStubAcceptingDashboardAdded();
        Dashboard validDashboard = new DashboardBuilder().build();

        CommandResult commandResult = new AddTaskCommand(validDashboard).execute(modelStub);

        assertEquals(String.format(AddTaskCommand.MESSAGE_SUCCESS, validDashboard), commandResult.getFeedbackToUser());
        assertEquals(Collections.singletonList(validDashboard), modelStub.dashboardsAdded);
    }

    @Test
    public void execute_duplicateDashboard_throwsCommandException() {
        Dashboard validDashboard = new DashboardBuilder().build();
        AddTaskCommand addCommand = new AddTaskCommand(validDashboard);
        ModelStub modelStub = new ModelStubWithDashboard(validDashboard);

        Assert.assertThrows(CommandException.class,
                AddTaskCommand.MESSAGE_DUPLICATE_TASK, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Dashboard task1 = new DashboardBuilder().withDashboardName("Task1")
                .withTaskDate("12/12/2019").withTaskStatus("COMPLETE").build();
        Dashboard task2 = new DashboardBuilder().withDashboardName("Task2")
                .withTaskDate("12/12/2019").withTaskStatus("COMPLETE").build();

        AddTaskCommand addTask1Command = new AddTaskCommand(task1);
        AddTaskCommand addTask2Command = new AddTaskCommand(task2);

        // same object -> returns true
        assertTrue(addTask1Command.equals(addTask1Command));

        // same values -> returns true
        AddTaskCommand addTask1CommandCopy = new AddTaskCommand(task1);
        assertTrue(addTask1Command.equals(addTask1CommandCopy));

        // different types -> returns false
        assertFalse(addTask1Command.equals(1));

        // null -> returns false
        assertFalse(addTask1Command.equals(null));

        // different dashboards -> returns false
        assertFalse(addTask1Command.equals(addTask2Command));
    }


    /**
     * A Model stub that contains a single dashboard.
     */
    private class ModelStubWithDashboard extends ModelStub {
        private final Dashboard dashboard;

        ModelStubWithDashboard(Dashboard dashboard) {
            requireNonNull(dashboard);
            this.dashboard = dashboard;
        }

        @Override
        public boolean hasDashboard(Dashboard dashboard) {
            requireNonNull(dashboard);
            return this.dashboard.isSameDashboard(dashboard);
        }
    }

    /**
     * A Model stub that always accept the dashboard being added.
     */
    private class ModelStubAcceptingDashboardAdded extends ModelStub {
        final ArrayList<Dashboard> dashboardsAdded = new ArrayList<>();

        @Override
        public boolean hasDashboard(Dashboard dashboard) {
            requireNonNull(dashboard);
            return dashboardsAdded.stream().anyMatch(dashboard::isSameDashboard);
        }

        @Override
        public void addDashboard(Dashboard dashboard) {
            requireNonNull(dashboard);
            dashboardsAdded.add(dashboard);
        }

        @Override
        public ReadOnlyDashboard getDashboardRecords() {
            return new DashboardRecords();
        }
    }

}
